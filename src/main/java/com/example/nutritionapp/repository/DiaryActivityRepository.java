package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.DiaryActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiaryActivityRepository extends JpaRepository<DiaryActivity, UUID> {
    Optional<DiaryActivity> findFirstByDiaryIdAndActivityId(UUID diaryId, UUID activityId);
    List<DiaryActivity> findAllByDiaryId(UUID diaryId);
}
