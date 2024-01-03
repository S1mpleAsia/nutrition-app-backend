package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.Activity;
import com.example.nutritionapp.dto.ActivityDTO;
import com.example.nutritionapp.exception.impl.InstanceNotFoundException;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.mapper.ActivityMapper;
import com.example.nutritionapp.repository.ActivityRepository;
import com.example.nutritionapp.repository.UserRepository;
import com.example.nutritionapp.utils.type.ActivityStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final ActivityMapper activityMapper;

    public GeneralListResponse<ActivityDTO> getAllActivity(UUID userId) {
        List<Activity> activityList = activityRepository.findAllByUserId(userId);

        return GeneralListResponse.success(activityMapper.toDtoList(activityList));
    }

    public GeneralResponse<ActivityDTO> getActivityDetail(UUID activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() ->
                new InstanceNotFoundException("Can not found any activity"));

        return GeneralResponse.success(activityMapper.toDto(activity));
    }

    public GeneralResponse<ActivityDTO> createActivity(UUID userId, ActivityDTO activityDTO) {
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Could not found any user"));

        Activity activity = Activity.builder()
                .name(activityDTO.getName())
                .caloriesConsume(activityDTO.getCaloriesConsume())
                .userId(userId)
                .status(ActivityStatus.UNPUBLISHED.name())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        return GeneralResponse.success(activityMapper.toDto(savedActivity));
    }
}
