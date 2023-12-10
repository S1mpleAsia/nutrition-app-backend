package com.example.nutritionapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "diary_activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID activityId;
    private UUID diaryId;
    private Double minutes;
    @Transient
    private Activity activity;
    @Transient
    private Diary diary;
}
