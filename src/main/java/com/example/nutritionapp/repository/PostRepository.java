package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByDiaryId(UUID diaryId);
}
