package com.example.service;

import com.example.model.Dish;
import com.example.model.Meal;
import com.example.model.User;
import com.example.repository.MealRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;

    public Meal createMealWithDishAndUser(List<Dish> dishes, User user) {
        Meal meal = new Meal();
        meal.setUser(user);
        meal.setDishes(dishes);
        return mealRepository.save(meal);
    }

    public Meal findMealById(Integer id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meal with ID " + id + " not found"));
    }

    public List<Meal> findAll() {
        return mealRepository.findAll();
    }

    public List<Meal> findMealsByDate(LocalDate date) {
        return mealRepository.findMealsByDate(date);
    }
}
