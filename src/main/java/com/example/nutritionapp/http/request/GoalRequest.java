package com.example.nutritionapp.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalRequest {
    private UUID userId;
    private Double bmi;
    private Integer tdee;
    private Integer water;
}
