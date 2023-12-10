package com.example.nutritionapp.mapper;

import com.example.nutritionapp.domain.Activity;
import com.example.nutritionapp.dto.ActivityDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    ActivityDTO toDto(Activity activity);
    Activity toEntity(ActivityDTO activityDTO);
    List<ActivityDTO> toDtoList(List<Activity> activities);
}
