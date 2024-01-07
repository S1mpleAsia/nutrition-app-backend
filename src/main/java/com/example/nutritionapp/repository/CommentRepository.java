package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByPostId(UUID postId);
    List<Comment> findAllByCommentFromUserId(UUID commentFromUserId);
    void deleteAllByPostId(UUID postId);
}
