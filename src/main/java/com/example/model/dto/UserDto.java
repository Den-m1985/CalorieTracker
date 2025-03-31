package com.example.model.dto;

import com.example.model.enums.Gender;
import com.example.model.enums.Goal;

public record UserDto(
        Integer id,
        String firstName,
        String middleName,
        String lastName,
        String email,
        String password,
        Integer age,
        Double weight,
        Double height,
        Goal goal,
        Gender gender,
        Double dailyCalories
) {
}
