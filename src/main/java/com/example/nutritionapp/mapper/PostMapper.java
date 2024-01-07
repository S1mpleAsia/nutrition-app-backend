package com.example.nutritionapp.mapper;

import com.example.nutritionapp.domain.Post;
import com.example.nutritionapp.dto.PostDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO toDto(Post post);
    Post toEntity(PostDTO postDTO);
    List<PostDTO> toDtoList(List<Post> postList);
}
