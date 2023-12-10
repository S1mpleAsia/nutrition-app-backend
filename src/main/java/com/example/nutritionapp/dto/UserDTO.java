package com.example.nutritionapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
