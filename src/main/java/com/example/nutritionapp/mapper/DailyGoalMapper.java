package com.example.nutritionapp.mapper;

import com.example.nutritionapp.domain.DailyGoal;
import com.example.nutritionapp.dto.DailyGoalDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DailyGoalMapper {
    DailyGoalDTO toDto(DailyGoal dailyGoal);
    DailyGoal toEntity(DailyGoalDTO dailyGoalDTO);
}
