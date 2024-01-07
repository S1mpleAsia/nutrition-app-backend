package com.example.nutritionapp.mapper;

import com.example.nutritionapp.domain.Report;
import com.example.nutritionapp.dto.ReportDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    ReportDTO toDto(Report report);
    Report toEntity(ReportDTO reportDTO);
    List<ReportDTO> toDtoList(List<Report> reportList);
    List<Report> toEntityList(List<ReportDTO> reportDTOList);
}
