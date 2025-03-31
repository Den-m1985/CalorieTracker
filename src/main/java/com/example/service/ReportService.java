package com.example.service;

import com.example.model.Dish;
import com.example.model.Meal;
import com.example.model.dto.DailyReport;
import com.example.model.dto.MealHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final MealService mealService;

    @Transactional(readOnly = true)
    public DailyReport generateDailyReport(LocalDate date) {
        List<Meal> meals = mealService.findMealsByDate(date);
        int totalCalories = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(Dish::getCalories)
                .sum();
        return new DailyReport(totalCalories);
    }

    @Transactional(readOnly = true)
    public boolean isUserWithinCalorieLimit(int dailyCalorieLimit, LocalDate date) {
        DailyReport report = generateDailyReport(date);
        return report.totalCalories() <= dailyCalorieLimit;
    }

    @Transactional(readOnly = true)
    public MealHistory getMealHistoryByDay(LocalDate date) {
        List<Meal> meals = mealService.findMealsByDate(date);
        int totalCalories = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(Dish::getCalories)
                .sum();
        return new MealHistory(meals, totalCalories);
    }

}
