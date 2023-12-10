package com.example.nutritionapp.mapper;

import com.example.nutritionapp.domain.PostInteraction;
import com.example.nutritionapp.dto.PostInteractionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostInteractionMapper {
    PostInteractionDTO toDto(PostInteraction postInteraction);
    PostInteraction toEntity(PostInteractionDTO postInteractionDTO);
}
