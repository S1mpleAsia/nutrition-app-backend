package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.DailyGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DailyGoalRepository extends JpaRepository<DailyGoal, UUID> {
    DailyGoal findFirstByUserId(UUID userId);
}
