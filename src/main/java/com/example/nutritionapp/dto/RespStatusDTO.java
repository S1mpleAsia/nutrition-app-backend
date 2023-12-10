package com.example.nutritionapp.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespStatusDTO {
    private Integer code;
    private String message;
}
