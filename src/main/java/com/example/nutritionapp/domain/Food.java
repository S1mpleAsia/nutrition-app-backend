package com.example.nutritionapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "food")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;
    private String foodName;
    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
    private String image;
    private String unitType;   // food unit
    private UUID userId;
    private String status;
    @Transient
    private User user;
}
