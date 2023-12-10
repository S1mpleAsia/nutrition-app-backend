package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.Activity;
import com.example.nutritionapp.dto.ActivityDTO;
import com.example.nutritionapp.exception.impl.InstanceNotFoundException;
import com.example.nutritionapp.mapper.ActivityMapper;
import com.example.nutritionapp.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public List<ActivityDTO> getAllActivity() {
        List<Activity> activityList = activityRepository.findAll();

        return activityMapper.toDtoList(activityList);
    }

    public ActivityDTO getACtivityDetail(UUID activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() ->
                new InstanceNotFoundException("Can not found any activity"));

        return activityMapper.toDto(activity);
    }

    public void createActivity(ActivityDTO activityDTO) {
        activityRepository.save(activityMapper.toEntity(activityDTO));
    }
}
