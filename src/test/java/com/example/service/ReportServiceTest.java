package com.example.service;

import com.example.mapper.DishMapper;
import com.example.mapper.UserMapper;
import com.example.model.Dish;
import com.example.model.Meal;
import com.example.model.User;
import com.example.model.dto.DailyReport;
import com.example.model.dto.DishDto;
import com.example.model.dto.MealDto;
import com.example.model.dto.MealHistory;
import com.example.model.dto.UserDto;
import com.example.model.enums.Gender;
import com.example.model.enums.Goal;
import com.example.repository.DishRepository;
import com.example.repository.MealRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ReportServiceTest {
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DishService dishService;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private MealService mealService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReportService reportService;

    private MealDto mealDto;
    private Meal meal;
    private Dish dish1;
    private Dish dish2;
    private UserDto userDto;
    private User user;

    @BeforeEach
    public void setUp() {
        mealRepository.deleteAll();
        dishRepository.deleteAll();
        userRepository.deleteAll();

        dish1 = new Dish();
        dish1.setName("Pasta");
        dish1.setCalories(500);
        dish1.setProteins(15);
        dish1.setFats(10);
        dish1.setCarbohydrates(70);
        dish1 = dishService.saveDish(dish1);
        DishDto dishDto1 = dishMapper.toDto(dish1);

        dish2 = new Dish();
        dish2.setName("Salad");
        dish2.setCalories(200);
        dish2.setProteins(5);
        dish2.setFats(7);
        dish2.setCarbohydrates(20);
        dish2 = dishService.saveDish(dish2);
        DishDto dishDto2 = dishMapper.toDto(dish2);

        userDto = new UserDto(
                null,
                "First name",
                "Middle name",
                "Last name",
                "john.doe@example.com",
                "password",
                30,
                100.0,
                180.0,
                Goal.MAINTAIN_WEIGHT,
                Gender.FEMALE,
                1975.0
        );
        user = userMapper.toEntity(userDto);
        user = userService.saveUser(user);

        mealDto = new MealDto(
                null,
                userDto,
                Arrays.asList(dishDto1, dishDto2));

        meal = mealService.createMealWithDishAndUser(Arrays.asList(dish1, dish2), user);
    }

    @Test
    public void shouldGenerateDailyReportWithTotalCalories() {
        LocalDate date = LocalDate.now();
        DailyReport report = reportService.generateDailyReport(date);

        assertNotNull(report);
        assertEquals(700, report.totalCalories());
    }

    @Test
    public void shouldCheckIfUserIsWithinDailyCalorieLimit() {
        int dailyCalorieLimit = 2000;  // Пример дневной нормы
        LocalDate date = LocalDate.now();
        boolean isWithinLimit = reportService.isUserWithinCalorieLimit(dailyCalorieLimit, date);

        assertThat(isWithinLimit).isTrue();
    }

    @Test
    public void shouldGenerateMealHistoryByDays() {
        LocalDate date = LocalDate.now();
        MealHistory history = reportService.getMealHistoryByDay(date);

        assertNotNull(history);
        assertEquals(1, history.meals().size());
        assertEquals(700, history.totalCalories());
    }

}
