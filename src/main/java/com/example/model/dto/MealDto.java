package com.example.model.dto;

import java.util.List;

public record MealDto(
        Integer id,
        UserDto user,
        List<DishDto> dishes
) {
}
