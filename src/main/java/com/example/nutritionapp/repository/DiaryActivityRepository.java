package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.DiaryActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiaryActivityRepository extends JpaRepository<DiaryActivity, UUID> {
}
