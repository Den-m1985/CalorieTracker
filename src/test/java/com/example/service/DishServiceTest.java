package com.example.service;

import com.example.mapper.DishMapper;
import com.example.model.Dish;
import com.example.model.dto.DishDto;
import com.example.repository.DishRepository;
import com.example.repository.MealRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class DishServiceTest {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishService dishService;
    @Autowired
    private MealRepository mealRepository;

    private DishDto dishDto;
    private Dish dish;

    private String name = "Pasta";
    private int calories = 500;
    private int proteins = 15;
    private int fats = 10;
    private int carbs = 70;

    @BeforeEach
    public void setUp() {
        mealRepository.deleteAll();
        dishRepository.deleteAll();
        dishDto = new DishDto(
                null,
                name,
                calories,
                proteins,
                fats,
                carbs
        );
        dish = dishMapper.toEntity(dishDto);
        dish = dishService.saveDish(dish);
    }

    @Test
    public void shouldCreateDishSuccessfully() {
        DishDto createdDish = dishService.createDish(dishDto);
        assertEquals(createdDish.name(), dishDto.name());
        assertEquals(createdDish.calories(), dishDto.calories());
        assertEquals(createdDish.proteins(), dishDto.proteins());
        assertEquals(createdDish.fats(), dishDto.fats());
        assertEquals(createdDish.fats(), dishDto.fats());
    }

    @Test
    public void shouldReturnDishById() {
        Dish resultDish = dishService.findDishById(dish.getId());
        assertEquals(resultDish.getId(), dish.getId());
    }

    @Test
    public void shouldThrowExceptionWhenDishNotFound() {
        assertThatThrownBy(() -> dishService.findDishById(dish.getId() + 1))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
