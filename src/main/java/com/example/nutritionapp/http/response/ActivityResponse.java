package com.example.nutritionapp.http.response;

import com.example.nutritionapp.dto.ActivityDTO;
import com.example.nutritionapp.dto.UserDTO;
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
public class ActivityResponse {
    private UUID id;
    private String name;
    private Double caloriesConsume;
    private UUID userId;
    private String status;
    private UserDTO user;
    private Double minutes;
    private Double totalConsumes;
}
