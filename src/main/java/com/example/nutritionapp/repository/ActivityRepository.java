package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    List<Activity> findAllByUserId(UUID userId);
    List<Activity> findAllByUserIdAndStatus(UUID userId, String status);
    List<Activity> findAllByStatus(String status);
}
