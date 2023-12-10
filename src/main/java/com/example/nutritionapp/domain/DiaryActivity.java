package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "diary_activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryActivity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;
    private UUID activityId;
    private UUID diaryId;
    private Double minutes;
    @Transient
    private Activity activity;
    @Transient
    private Diary diary;
}
