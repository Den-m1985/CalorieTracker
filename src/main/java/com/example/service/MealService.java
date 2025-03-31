package com.example.service;

import com.example.mapper.MealMapper;
import com.example.model.Dish;
import com.example.model.Meal;
import com.example.model.User;
import com.example.model.dto.MealDto;
import com.example.repository.MealRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final UserService userService;
    private final MealMapper mealMapper;

    @Transactional
    public MealDto addMeal(MealDto mealDto) {
        User user = userService.findUserById(mealDto.user().id());
        Meal meal = mealMapper.toEntity(mealDto);
        meal.setUser(user);
        meal = mealRepository.save(meal);
        return mealMapper.toDto(meal);
    }

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

    public MealDto getMealById(MealDto mealDto) {
        Meal meal = findMealById(mealDto.id());
        return mealMapper.toDto(meal);
    }

    public List<Meal> findAll() {
        return mealRepository.findAll();
    }

    public List<Meal> findMealsByDate(LocalDate date) {
        return mealRepository.findMealsByDate(date);
    }

    public List<MealDto> getMealsByUser(MealDto mealDto) {
        User user = userService.findUserById(mealDto.user().id());
        List<Meal> meals = mealRepository.findByUser(user);
        return meals.stream()
                .map(mealMapper::toDto)
                .collect(Collectors.toList());
    }
}
