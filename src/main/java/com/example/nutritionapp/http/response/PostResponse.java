package com.example.nutritionapp.http.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private UUID id;
    private UUID diaryId;
    private String content;
    private String image;
    private UUID userId;
    private String username;
}
