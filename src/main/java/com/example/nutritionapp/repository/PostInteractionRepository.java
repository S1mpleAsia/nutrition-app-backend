package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.PostInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostInteractionRepository extends JpaRepository<PostInteraction, UUID> {
    List<PostInteraction> findAllByPostId(UUID postId);
    void deleteAllByPostId(UUID postId);
    Optional<PostInteraction> findFirstByFromUserIdAndPostId(UUID fromUserId, UUID postId);
}
