package com.example.nutritionapp.dto;

import com.example.nutritionapp.domain.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private UUID id;
    private UUID postId;
    private UUID commentUserId;
    private UUID commentParentId;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private String status;
    private Post post;
    private UserDTO commentUser;
    private UserDTO commentParent;
}
