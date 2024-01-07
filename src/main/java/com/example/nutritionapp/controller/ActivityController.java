package com.example.nutritionapp.controller;

import com.example.nutritionapp.dto.ActivityDTO;
import com.example.nutritionapp.http.request.FoodApproveRequest;
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

    @PostMapping()
    public GeneralResponse<ActivityDTO> createActivity(@RequestBody ActivityDTO request) {
        return activityService.createActivity(request);
    }

    @PutMapping("{activityId}")
    public GeneralResponse<ActivityDTO> publishedActivity(@PathVariable("activityId") UUID activityId, @RequestBody FoodApproveRequest request) {
        return activityService.publishedActivity(activityId, request);
    }

    @PutMapping("approve/{activityId}")
    public GeneralResponse<ActivityDTO> approveActivity(@PathVariable("activityId") UUID activityId, @RequestBody FoodApproveRequest request) {
        return activityService.approveFood(activityId, request);
    }

    @DeleteMapping("{activityId}")
    public void deleteActivity(@PathVariable("activityId") UUID activityId) {
        activityService.deleteActivity(activityId);
    }

    @GetMapping("public")
    public GeneralListResponse<ActivityDTO> getPendingActivity() {
        return activityService.getPendingListActivity();
    }

    @PostMapping("{activityId}")
    public GeneralResponse<ActivityDTO> updateActivity(@PathVariable("activityId") UUID activityId,@RequestBody ActivityDTO activityDTO ) {
        return activityService.updateActivity(activityId, activityDTO);
    }
}
