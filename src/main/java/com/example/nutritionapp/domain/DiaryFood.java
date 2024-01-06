package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "diary_food")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryFood {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 16)
    private UUID id;
    @Column(length = 16)
    private UUID foodId;
    @Column(length = 16)
    private UUID diaryId;
    private Double amount;
    @Transient
    private Food food;
    @Transient
    private Diary diary;
}
