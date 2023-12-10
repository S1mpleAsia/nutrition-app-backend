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
public class DiaryActivityDTO {
    private UUID id;
    private UUID activityId;
    private UUID diaryId;
    private Double minutes;
    private ActivityDTO activity;
    private DiaryDTO diary;
}
