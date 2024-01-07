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
public class PostDTO {
    private UUID id;
    private UUID diaryId;
    private String content;
    private String image;
    private DiaryDTO diary;
}
