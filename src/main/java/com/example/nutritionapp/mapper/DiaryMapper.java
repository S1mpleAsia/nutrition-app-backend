package com.example.nutritionapp.mapper;

import com.example.nutritionapp.domain.Diary;
import com.example.nutritionapp.dto.DiaryDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiaryMapper {
    DiaryDTO toDto(Diary diary);
    Diary toEntity(DiaryDTO diaryDTO);
    List<DiaryDTO> toDtoList(List<Diary> diaryList);
}
