package com.example.model.dto;

public record DishDto(
        Integer id,
        String name,
        int calories,
        int proteins,
        int fats,
        int carbs
) {
}
