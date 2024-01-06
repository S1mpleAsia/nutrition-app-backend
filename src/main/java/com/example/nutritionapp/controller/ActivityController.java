package com.example.nutritionapp.controller;

import com.example.nutritionapp.dto.ActivityDTO;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/activity")
@CrossOrigin
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping("{userId}")
    public GeneralListResponse<ActivityDTO> getAllActivity(@PathVariable("userId") UUID userId) {
        return activityService.getAllActivity(userId);
    }

    @GetMapping("/detail/{activityId}")
    public GeneralResponse<ActivityDTO> getActivityDetail(@PathVariable("activityId") UUID activityId) {
        return activityService.getActivityDetail(activityId);
    }

    @PostMapping("{userId}")
    public GeneralResponse<ActivityDTO> createActivity(@PathVariable("userId") UUID userId, @RequestBody ActivityDTO activityDTO) {
        return activityService.createActivity(userId, activityDTO);
    }
}
