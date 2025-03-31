package com.example.mapper;

import com.example.model.Meal;
import com.example.model.dto.MealDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MealMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "dishes", source = "dishes")
    MealDto toDto(Meal meal);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "dishes", ignore = true)
    Meal toEntity(MealDto mealDto);
}
