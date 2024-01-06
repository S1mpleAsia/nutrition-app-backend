package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiaryRepository extends JpaRepository<Diary, UUID> {
    List<Diary> findAllByUserId(UUID userId);
    Optional<Diary> findFirstByUserIdAndDate(UUID userId, Date date);
}
