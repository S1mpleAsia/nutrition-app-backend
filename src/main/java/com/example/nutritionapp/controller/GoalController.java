package com.example.nutritionapp.controller;

import com.example.nutritionapp.dto.DailyGoalDTO;
import com.example.nutritionapp.http.request.GoalRequest;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    @PostMapping("/{userId}")
    public GeneralResponse<DailyGoalDTO> createGoal(@PathVariable("userId") UUID userId, @RequestBody DailyGoalDTO goalRequest) {
        return goalService.createGoal(userId, goalRequest);
    }

    @GetMapping("/{userId}")
    public GeneralResponse<DailyGoalDTO> getPersonalGoal(@PathVariable("userId") UUID userId) {
        return goalService.getPersonalGoal(userId);
    }
}
