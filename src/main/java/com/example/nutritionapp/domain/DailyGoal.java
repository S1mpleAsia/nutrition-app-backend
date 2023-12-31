package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "daily_goal")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyGoal {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 16)
    private UUID id;
    @Column(length = 16)
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
