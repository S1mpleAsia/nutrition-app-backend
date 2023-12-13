package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type = "uuid-char")
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
