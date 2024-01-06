package com.example.nutritionapp.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryUpdateRequest {
    private String date;
    private Integer water; // Number of glass
    private UUID foodId;
    private Double foodAmount;
    private UUID activityId;
    private Double activityUnit; // Minutes work
}
