package com.example.controller;

import com.example.model.dto.MealDto;
import com.example.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
public class MealController implements MealApi{
    private final MealService mealService;

    @PostMapping
    public ResponseEntity<MealDto> addMeal(@RequestBody MealDto mealDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mealService.addMeal(mealDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDto> getMeal(@RequestBody MealDto mealDto) {
        return ResponseEntity.ok(mealService.getMealById(mealDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealDto>> getUserMeals(@RequestBody MealDto mealDto) {
        return ResponseEntity.ok(mealService.getMealsByUser(mealDto));
    }
}
