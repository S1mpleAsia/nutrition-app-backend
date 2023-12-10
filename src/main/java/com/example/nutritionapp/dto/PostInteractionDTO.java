package com.example.nutritionapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostInteractionDTO {
    private UUID id;
    private UUID postId;
    private UUID fromUserId;
    private Boolean isLiked;
    private PostDTO post;
    private UserDTO fromUser;
}
