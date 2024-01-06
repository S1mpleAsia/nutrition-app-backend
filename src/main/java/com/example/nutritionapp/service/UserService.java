package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.DailyGoal;
import com.example.nutritionapp.domain.User;
import com.example.nutritionapp.dto.UserDTO;
import com.example.nutritionapp.http.request.InitAccountRequest;
import com.example.nutritionapp.http.request.UserInfoRequest;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.http.response.UserResponse;
import com.example.nutritionapp.mapper.DailyGoalMapper;
import com.example.nutritionapp.mapper.UserMapper;
import com.example.nutritionapp.repository.DailyGoalRepository;
import com.example.nutritionapp.repository.UserRepository;
import com.example.nutritionapp.utils.type.AccountStatus;
import com.example.nutritionapp.utils.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DailyGoalRepository dailyGoalRepository;
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

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
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
