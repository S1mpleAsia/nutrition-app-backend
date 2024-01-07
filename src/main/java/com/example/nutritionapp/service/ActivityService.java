package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.Activity;
import com.example.nutritionapp.domain.User;
import com.example.nutritionapp.dto.ActivityDTO;
import com.example.nutritionapp.exception.impl.InstanceNotFoundException;
import com.example.nutritionapp.http.request.FoodApproveRequest;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.mapper.ActivityMapper;
import com.example.nutritionapp.repository.ActivityRepository;
import com.example.nutritionapp.repository.UserRepository;
import com.example.nutritionapp.utils.type.ActivityStatus;
import com.example.nutritionapp.utils.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final ActivityMapper activityMapper;

    public GeneralListResponse<ActivityDTO> getAllActivity(UUID userId) {
        List<Activity> activityList = activityRepository.findAllByUserId(userId);
        List<Activity> unpublishedList = activityList.stream().filter(activity ->
                Objects.equals(activity.getStatus(), ActivityStatus.UNPUBLISHED.name())).toList();

        List<Activity> pendingList = activityRepository.findAllByUserIdAndStatus(userId, ActivityStatus.PENDING.name());
        List<Activity> publishedList = activityRepository.findAllByStatus(ActivityStatus.PUBLISHED.name());

        List<Activity> result = Stream.concat(
                Stream.concat(unpublishedList.stream(), pendingList.stream()),
                publishedList.stream()).toList();

        return GeneralListResponse.success(activityMapper.toDtoList(result));
    }

    public GeneralResponse<ActivityDTO> getActivityDetail(UUID activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() ->
                new InstanceNotFoundException("Can not found any activity"));

        return GeneralResponse.success(activityMapper.toDto(activity));
    }

    public GeneralResponse<ActivityDTO> createActivity(ActivityDTO request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("Could not found any user"));

        Activity activity = activityMapper.toEntity(request);

        if(user.getRole().equals(Role.USER.name())) {
            activity.setStatus(ActivityStatus.UNPUBLISHED.name());
        } else if (user.getRole().equals(Role.ADMIN.name())) {
            activity.setStatus(ActivityStatus.PUBLISHED.name());
        }

        Activity insertedActivity = activityRepository.saveAndFlush(activity);

        return GeneralResponse.success(activityMapper.toDto(insertedActivity));
    }

    public GeneralResponse<ActivityDTO> publishedActivity(UUID activityId, FoodApproveRequest request) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new RuntimeException("Could not find any food"));

        if(request.getStatus().equals(ActivityStatus.PENDING.name())) {
            activity.setStatus(ActivityStatus.PENDING.name());
            activityRepository.save(activity);

            return GeneralResponse.success(activityMapper.toDto(activity));
        } else if (request.getStatus().equals(ActivityStatus.UNPUBLISHED.name())) {
            activity.setStatus(ActivityStatus.UNPUBLISHED.name());
            activityRepository.save(activity);

            return GeneralResponse.success(activityMapper.toDto(activity));
        } else {
            throw new RuntimeException("Status is invalid");
        }
    }

    public GeneralResponse<ActivityDTO> approveFood(UUID activityId, FoodApproveRequest request) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new RuntimeException("Could not find any food"));

        if(request.getStatus().equals(ActivityStatus.PUBLISHED.name())) {
            activity.setStatus(ActivityStatus.PUBLISHED.name());
            activityRepository.save(activity);

            return GeneralResponse.success(activityMapper.toDto(activity));
        } else if (request.getStatus().equals(ActivityStatus.UNPUBLISHED.name())) {
            activity.setStatus(ActivityStatus.UNPUBLISHED.name());
            activityRepository.save(activity);

            return GeneralResponse.success(activityMapper.toDto(activity));
        } else {
            throw new RuntimeException("Status is invalid");
        }
    }

    public void deleteActivity(UUID activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new RuntimeException("Could not find any activity"));

        activityRepository.delete(activity);
    }

    public GeneralListResponse<ActivityDTO> getPendingListActivity() {
        List<Activity> activityList = activityRepository.findAllByStatus(ActivityStatus.PENDING.name());

        return GeneralListResponse.success(activityMapper.toDtoList(activityList));
    }

    public GeneralResponse<ActivityDTO> updateActivity(UUID activityId, ActivityDTO activityDTO) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new RuntimeException("Could not find any activity"));

        activityRepository.save(activity);

        return GeneralResponse.success(activityMapper.toDto(activity));
    }


}
