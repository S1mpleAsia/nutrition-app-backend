package com.example.nutritionapp.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportPostRequest {
    private UUID userId;
    private String reason;
    private UUID postId;
}
