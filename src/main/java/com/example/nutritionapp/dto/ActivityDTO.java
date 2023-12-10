package com.example.nutritionapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityDTO {
    private UUID id;
    private String name;
    private Double caloriesConsume;
    private UUID userId;
    private String status;
    private UserDTO user;
}
