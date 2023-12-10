package com.example.nutritionapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "post_interaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID postId;
    private UUID fromUserId;
    private Boolean isLiked;
    @Transient
    private Post post;
    @Transient
    private User fromUser;
}
