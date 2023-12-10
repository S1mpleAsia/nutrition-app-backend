package com.example.nutritionapp.mapper;

import com.example.nutritionapp.domain.Food;
import com.example.nutritionapp.dto.FoodDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    FoodDTO toDto(Food food);
    Food toEntity(FoodDTO foodDTO);
    List<FoodDTO> toDtoList(List<Food> foodList);
}
