package com.example.nutritionapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "daily_goal")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID userId;
    private Double bmi;
    private Integer tdee;
    private Integer water;
    private Integer carbs;
    private Integer fat;
    private Integer protein;
    @Transient
    private User user;
}
