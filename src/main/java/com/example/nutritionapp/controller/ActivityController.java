package com.example.nutritionapp.controller;

import com.example.nutritionapp.dto.ActivityDTO;
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

    @GetMapping
    public List<ActivityDTO> getAllActivity() {
        return activityService.getAllActivity();
    }

    @GetMapping("/{activityId}")
    public ActivityDTO getActivityDetail(@PathVariable("activityId") UUID activityId) {
        return activityService.getACtivityDetail(activityId);
    }

    @PostMapping
    public void createActivity(@RequestBody ActivityDTO activityDTO) {
        activityService.createActivity(activityDTO);
    }
}
