package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "diary_activity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryActivity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 16)
    private UUID id;
    @Column(length = 16)
    private UUID activityId;
    @Column(length = 16)
    private UUID diaryId;
    private Double minutes;
    @Transient
    private Activity activity;
    @Transient
    private Diary diary;
}
