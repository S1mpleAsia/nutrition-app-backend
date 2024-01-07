package com.example.nutritionapp.mapper;

import com.example.nutritionapp.domain.Comment;
import com.example.nutritionapp.dto.CommentDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDTO toDto(Comment comment);
    Comment toEntity(CommentDTO commentDTO);
    List<CommentDTO> toDtoList(List<Comment> commentList);
}
