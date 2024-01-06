package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.DiaryFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiaryFoodRepository extends JpaRepository<DiaryFood, UUID> {
    Optional<DiaryFood> findFirstByDiaryIdAndFoodId(UUID diaryId, UUID foodId);
    List<DiaryFood> findAllByDiaryId(UUID diaryId);
}
