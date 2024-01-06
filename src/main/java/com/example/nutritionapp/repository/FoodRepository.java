package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {
    List<Food> findAllByUserId(UUID userId);
    List<Food> findAllByStatus(String status);
}
