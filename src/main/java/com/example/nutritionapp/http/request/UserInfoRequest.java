package com.example.nutritionapp.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {
    private UUID id;
    private Integer age;
    private String gender;
    private Double height;
    private Double weight;
    private Integer role;
    private String status;
}
