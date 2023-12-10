package com.example.nutritionapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private UUID id;
    @NotNull
    @Size(min = 8)
    private String username;
    @NotNull
    @Size(min = 8)
    private String password;
    @Min(0)
    @Max(150)
    private Integer age;
    private String gender;
    private Double height;
    private Double weight;
    private String role;
    private String status;
}
