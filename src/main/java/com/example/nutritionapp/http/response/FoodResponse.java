package com.example.nutritionapp.http.response;

import com.example.nutritionapp.dto.FoodDTO;
import com.example.nutritionapp.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodResponse {
    private UUID id;
    private String foodName;
    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
    private String image;
    private String unitType;
    private UUID userId;
    private String status;
    private UserDTO user;
    private Double amount;
}
