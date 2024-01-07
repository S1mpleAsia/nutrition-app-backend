package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 16)
    private UUID id;
    @Column(length = 16)
    private UUID postId;
    @Column(length = 16)
    private UUID commentFromUserId;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private String status;
    @Transient
    private Post post;
    @Transient
    private User commentUser;
    @PrePersist
    public void updateCreateTime() {
        createdAt = new Date();
    }

    @PreUpdate
    public void updateUpdateTime() {
        updatedAt = new Date();
    }
}
