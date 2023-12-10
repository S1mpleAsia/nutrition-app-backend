package com.example.nutritionapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "actual_stats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID diaryId;
    private UUID goalId;
    private Integer realTdee;
    private Integer realWater;
    private Integer realProtein;
    private Integer realFat;
    private Integer realCarbs;
    private Date day;
    @Transient
    private Diary diary;
    @Transient
    private DailyGoal goal;
}
