package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
}
