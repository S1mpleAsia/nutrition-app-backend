package com.example.nutritionapp.http.response;

import com.example.nutritionapp.dto.DailyGoalDTO;
import com.example.nutritionapp.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UserDTO profile;
    private DailyGoalDTO dailyGoal;
}
