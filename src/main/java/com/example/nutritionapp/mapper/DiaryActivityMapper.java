package com.example.nutritionapp.mapper;

import com.example.nutritionapp.domain.DiaryActivity;
import com.example.nutritionapp.dto.DiaryActivityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiaryActivityMapper {
    DiaryActivityDTO toDto(DiaryActivity diaryActivity);
    DiaryActivity toEntity(DiaryActivityDTO diaryActivityDTO);
}
