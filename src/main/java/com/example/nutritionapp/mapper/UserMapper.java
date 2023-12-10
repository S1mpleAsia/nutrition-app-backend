package com.example.nutritionapp.mapper;

import com.example.nutritionapp.domain.User;
import com.example.nutritionapp.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
