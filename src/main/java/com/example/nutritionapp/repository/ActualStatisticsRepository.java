package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.ActualStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ActualStatisticsRepository extends JpaRepository<ActualStatistics, UUID> {
    Optional<ActualStatistics> findFirstByDiaryId(UUID diaryId);
}
