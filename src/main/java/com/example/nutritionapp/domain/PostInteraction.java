package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "post_interaction")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostInteraction {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 16)
    private UUID id;
    @Column(length = 16)
    private UUID postId;
    @Column(length = 16)
    private UUID fromUserId;
    private Boolean isLiked;
    @Transient
    private Post post;
    @Transient
    private User fromUser;
}
