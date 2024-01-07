package com.example.nutritionapp.http.response;

import com.example.nutritionapp.domain.ActualStatistics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryUpdateResponse {
    private Double totalConsumes;
    private ActualStatistics statistics;
    private List<FoodResponse> foodList;
    private List<ActivityResponse> activityList;
}
