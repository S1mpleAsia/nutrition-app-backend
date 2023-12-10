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
public class DailyGoalDTO {
    private UUID id;
    private UUID userId;
    private Double bmi;
    private Integer tdee;
    private Integer water;
    private Integer carbs;
    private Integer fat;
    private Integer protein;
    private UserDTO user;
}
