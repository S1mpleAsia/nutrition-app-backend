package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "diary")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 16)
    private UUID id;
    private Date date;
    @Column(columnDefinition = "TEXT")
    private String note;
    @Column(length = 16)
    private UUID userId;
    @Transient
    private User user;
}
