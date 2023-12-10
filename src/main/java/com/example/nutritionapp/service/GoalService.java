package com.example.nutritionapp.service;

import com.example.nutritionapp.domain.DailyGoal;
import com.example.nutritionapp.domain.User;
import com.example.nutritionapp.dto.DailyGoalDTO;
import com.example.nutritionapp.exception.impl.InstanceExistedException;
import com.example.nutritionapp.exception.impl.InstanceNotFoundException;
import com.example.nutritionapp.http.request.GoalRequest;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.mapper.DailyGoalMapper;
import com.example.nutritionapp.repository.DailyGoalRepository;
import com.example.nutritionapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final DailyGoalRepository dailyGoalRepository;
    private final DailyGoalMapper dailyGoalMapper;
    private final UserRepository userRepository;

    public void createGoal(UUID userId, DailyGoalDTO goalRequest) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) throw new InstanceExistedException("Could not found any user");

        DailyGoal goal = DailyGoal.builder()
                .userId(userId)
                .bmi(goalRequest.getBmi())
                .tdee(goalRequest.getTdee())
                .water(goalRequest.getWater())
                .carbs(goalRequest.getCarbs())
                .protein(goalRequest.getProtein())
                .fat(goalRequest.getFat())
                .build();

        dailyGoalRepository.save(goal);
    }

    public GeneralResponse<DailyGoalDTO> getPersonalGoal(UUID userId) {
        userRepository.findById(userId).orElseThrow(() -> new InstanceNotFoundException("Could not found any user"));
        DailyGoal dailyGoal = dailyGoalRepository.findFirstByUserId(userId);

        return GeneralResponse.success(dailyGoalMapper.toDto(dailyGoal));
    }
}
