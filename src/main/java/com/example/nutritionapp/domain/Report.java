package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "report")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 16)
    private UUID id;
    private UUID userReportId;
    private UUID postId;
    @Column(columnDefinition = "TEXT", length = 16)
    private String reason;
    private String status;
    @Transient
    private Post post;
    @Transient
    private User userReport;
}
