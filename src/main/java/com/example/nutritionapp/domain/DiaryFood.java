package com.example.nutritionapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "diary_food")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryFood {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID foodId;
    private UUID diaryId;
    private Double amount;
    @Transient
    private Food food;
    @Transient
    private Diary diary;
}
