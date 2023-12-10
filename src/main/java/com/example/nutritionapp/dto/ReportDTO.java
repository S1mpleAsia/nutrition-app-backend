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
public class ReportDTO {
    private UUID id;
    private UUID userReportId;
    private UUID postId;
    private String reason;
    private String status;
    private PostDTO post;
    private UserDTO userReport;
}
