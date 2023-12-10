package com.example.nutritionapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActualStatisticsDTO {
    private UUID id;
    private UUID diaryId;
    private UUID goalId;
    private Integer realTdee;
    private Integer realWater;
    private Integer realProtein;
    private Integer realFat;
    private Integer realCarbs;
    private Date day;
    private DiaryDTO diary;
    private DailyGoalDTO goal;
}
