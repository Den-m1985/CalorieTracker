package com.example.model.dto;

import com.example.model.Meal;

import java.util.List;

public record MealHistory(
        List<Meal> meals,
        int totalCalories
) {
}
