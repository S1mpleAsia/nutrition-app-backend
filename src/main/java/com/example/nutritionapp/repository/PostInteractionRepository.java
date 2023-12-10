package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.PostInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostInteractionRepository extends JpaRepository<PostInteraction, UUID> {
}
