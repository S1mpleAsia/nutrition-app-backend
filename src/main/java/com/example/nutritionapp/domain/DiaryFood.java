package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "diary_food")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryFood {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;
    private UUID foodId;
    private UUID diaryId;
    private Double amount;
    @Transient
    private Food food;
    @Transient
    private Diary diary;
}
