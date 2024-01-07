package com.example.nutritionapp.http.response;

import com.example.nutritionapp.dto.CommentDTO;
import com.example.nutritionapp.dto.PostDTO;
import com.example.nutritionapp.dto.PostInteractionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private PostDTO post;
    private List<PostInteractionDTO> interaction;
    private List<CommentDTO> comment;
}
