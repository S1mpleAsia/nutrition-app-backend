package com.example.nutritionapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID postId;
    private UUID commentUserId;
    private UUID commentParentId;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private String status;
    @Transient
    private Post post;
    @Transient
    private User commentUser;
    @Transient
    private User commentParent;

    @PrePersist
    public void updateCreateTime() {
        createdAt = new Date();
    }

    @PreUpdate
    public void updateUpdateTime() {
        updatedAt = new Date();
    }
}
