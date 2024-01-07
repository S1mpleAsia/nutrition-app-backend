package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
    List<Report> findAllByPostId(UUID postId);
    List<Report> findAllByUserReportId(UUID userReportId);
}
