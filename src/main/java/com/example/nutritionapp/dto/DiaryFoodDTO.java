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
public class DiaryFoodDTO {
    private UUID id;
    private UUID foodId;
    private UUID diaryId;
    private Double amount;
    private FoodDTO food;
    private DiaryDTO diary;
}
