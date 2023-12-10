package com.example.nutritionapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "report")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID userReportId;
    private UUID postId;
    @Column(columnDefinition = "TEXT")
    private String reason;
    private String status;
    @Transient
    private Post post;
    @Transient
    private User userReport;
}
