package com.example.nutritionapp.mapper;

import com.example.nutritionapp.domain.DiaryFood;
import com.example.nutritionapp.dto.DiaryFoodDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiaryFoodMapper {
    DiaryFoodDTO toDto(DiaryFood diaryFood);
    DiaryFood toEntity(DiaryFoodDTO diaryFoodDTO);
}
