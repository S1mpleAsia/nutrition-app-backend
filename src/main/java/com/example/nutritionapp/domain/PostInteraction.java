package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "post_interaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInteraction {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private UUID postId;
    private UUID fromUserId;
    private Boolean isLiked;
    @Transient
    private Post post;
    @Transient
    private User fromUser;
}
