package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.*;
import com.example.nutritionapp.dto.UserDTO;
import com.example.nutritionapp.http.request.InitAccountRequest;
import com.example.nutritionapp.http.request.UserInfoRequest;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.http.response.UserResponse;
import com.example.nutritionapp.mapper.DailyGoalMapper;
import com.example.nutritionapp.mapper.UserMapper;
import com.example.nutritionapp.repository.*;
import com.example.nutritionapp.utils.type.AccountStatus;
import com.example.nutritionapp.utils.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DailyGoalRepository dailyGoalRepository;
    private final DiaryRepository diaryRepository;
    private final ActivityRepository activityRepository;
    private final ActualStatisticsRepository actualStatisticsRepository;
    private final CommentRepository commentRepository;
    private final DiaryActivityRepository diaryActivityRepository;
    private final DiaryFoodRepository diaryFoodRepository;
    private final FoodRepository foodRepository;
    private final PostRepository postRepository;
    private final PostInteractionRepository postInteractionRepository;
    private final ReportRepository reportRepository;
    private final UserMapper userMapper;
    private final DailyGoalMapper dailyGoalMapper;

    public UserDTO signUp(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    public GeneralResponse<UserDTO> signIn(UserDTO userDTO) {
        User user = userRepository.findFirstByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());

        if(user == null) {
            throw new RuntimeException("Could not found any account");
        }

        return GeneralResponse.success(userMapper.toDto(user));
    }

    public GeneralResponse<UserDTO> updateInfo(UUID userId, UserInfoRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Could not find user"));

        if(user.getStatus().equals(AccountStatus.INACTIVE.name())) user.setStatus(AccountStatus.ACTIVE.name());
        updateUser(user, request);

        User updateUser = userRepository.save(user);
        return GeneralResponse.success(userMapper.toDto(updateUser));
    }

    private void updateUser(User user, UserInfoRequest request) {
        if(request.getAge() != null) user.setAge(request.getAge());
        if(request.getGender() != null) user.setGender(request.getGender());
        if(request.getHeight() != null) user.setHeight(request.getHeight());
        if(request.getWeight() != null) user.setWeight(request.getWeight());
        if(request.getStatus() != null) user.setStatus(request.getStatus());

    }

    public GeneralResponse<UserDTO> changePassword(UUID userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Could not find any user"));
        user.setPassword(password);

        userRepository.save(user);
        return GeneralResponse.success(userMapper.toDto(user));
    }

    public GeneralResponse<UserResponse> viewProfile(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Could not find any user"));
        DailyGoal dailyGoal = dailyGoalRepository.findFirstByUserId(userId);

        UserResponse response = UserResponse.builder()
                .profile(userMapper.toDto(user))
                .dailyGoal(dailyGoalMapper.toDto(dailyGoal))
                .build();

        return GeneralResponse.success(response);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);

        DailyGoal dailyGoal = dailyGoalRepository.findFirstByUserId(userId);

        List<Diary> diaryList = diaryRepository.findAllByUserId(userId);
        List<Activity> activityList = activityRepository.findAllByUserId(userId);
        List<Food> foodList = foodRepository.findAllByUserId(userId);
        List<ActualStatistics> actualStatistics = diaryList.stream().map(item -> {
            Optional<ActualStatistics> statisticsOptional = actualStatisticsRepository.findFirstByDiaryId(item.getId());
            return statisticsOptional.orElse(null);
        }).toList();

        List<DiaryActivity> diaryActivities = diaryList.stream().map(item ->  diaryActivityRepository.findAllByDiaryId(item.getId()))
                .flatMap(List::stream).toList();
        List<DiaryFood> diaryFoods = diaryList.stream().map(item -> diaryFoodRepository.findAllByDiaryId(item.getId()))
                .flatMap(List::stream).toList();

        List<Post> postList = diaryList.stream().map(item -> postRepository.findAllByDiaryId(item.getId()))
                .flatMap(List::stream).toList();
        List<PostInteraction> postInteractions = postList.stream().map(item -> postInteractionRepository.findAllByPostId(item.getId()))
                .flatMap(List::stream).toList();
        List<Report> report = postList.stream().map(item -> reportRepository.findAllByPostId(item.getId()))
                .flatMap(List::stream).toList();
        List<Report> reportByUser = reportRepository.findAllByUserReportId(userId);
        List<Comment> commentList = postList.stream().map(item -> commentRepository.findAllByPostId(item.getId()))
                .flatMap(List::stream).toList();
        List<Comment> commentListFromUser = commentRepository.findAllByCommentFromUserId(userId);

        if(dailyGoal != null) dailyGoalRepository.delete(dailyGoal);
        diaryRepository.deleteAll(diaryList);
        activityRepository.deleteAll(activityList);
        foodRepository.deleteAll(foodList);
        actualStatisticsRepository.deleteAll(actualStatistics);
        diaryActivityRepository.deleteAll(diaryActivities);
        diaryFoodRepository.deleteAll(diaryFoods);
        postRepository.deleteAll(postList);
        postInteractionRepository.deleteAll(postInteractions);
        reportRepository.deleteAll(report);
        reportRepository.deleteAll(reportByUser);
        commentRepository.deleteAll(commentList);
        commentRepository.deleteAll(commentListFromUser);
    }


    public GeneralResponse<UserDTO> initAccount(InitAccountRequest request) {
        User user = userRepository.findFirstByUsernameAndPassword(request.getUsername(), request.getPassword());

        if(user != null) throw new RuntimeException("Account already exist in the system");

        User persistUser = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(Role.USER.name())
                .status(AccountStatus.INACTIVE.name())
                .build();

        User insertedUser = userRepository.saveAndFlush(persistUser);

        return GeneralResponse.success(userMapper.toDto(insertedUser));
    }

    public GeneralResponse<UserDTO> createAdminUser(InitAccountRequest request) {
        User user = userRepository.findFirstByUsernameAndPassword(request.getUsername(), request.getPassword());

        if(user != null) throw new RuntimeException("Account already exist in the system");

        User persistUser = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .status(AccountStatus.ACTIVE.name())
                .role(Role.ADMIN.name())
                .build();

        User insertedUser = userRepository.saveAndFlush(persistUser);

        return GeneralResponse.success(userMapper.toDto(insertedUser));
    }

    public GeneralListResponse<UserDTO> getAllUser() {
        List<User> userList = userRepository.findAllByRole(Role.USER.name());
        List<UserDTO> response = userMapper.toDtoList(userList);

        return GeneralListResponse.success(response);
    }

    public GeneralListResponse<UserDTO> getAllBannedUser() {
        List<User> userList = userRepository.findAllByRoleAndStatus(Role.USER.name(), AccountStatus.BAN.name());
        List<UserDTO> response = userMapper.toDtoList(userList);

        return GeneralListResponse.success(response);
    }
}
