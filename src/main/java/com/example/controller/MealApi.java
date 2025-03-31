package com.example.controller;

import com.example.model.dto.MealDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Meal Controller", description = "Управление приемами пищи")
public interface MealApi {

    @Operation(summary = "Добавить прием пищи", description = "Добавляет прием пищи с указанными блюдами")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Прием пищи успешно добавлен")
    })
    ResponseEntity<MealDto> addMeal(@RequestBody MealDto mealDto);

    @Operation(summary = "Получить прием пищи по ID", description = "Возвращает информацию о конкретном приеме пищи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Прием пищи найден"),
            @ApiResponse(responseCode = "404", description = "Прием пищи не найден")
    })
    ResponseEntity<MealDto> getMeal(@RequestBody MealDto mealDto);

    @Operation(summary = "Получить все приемы пищи пользователя", description = "Возвращает список всех приемов пищи пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список приемов пищи получен")
    })
    ResponseEntity<List<MealDto>> getUserMeals(@RequestBody MealDto mealDto);
}
