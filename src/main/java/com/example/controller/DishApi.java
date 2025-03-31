package com.example.controller;

import com.example.model.dto.DishDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Dish Controller", description = "Управление блюдами")
public interface DishApi {

    @Operation(summary = "Получить все блюда", description = "Возвращает список всех доступных блюд.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список блюд успешно получен")
    })
    ResponseEntity<List<DishDto>> getAllDishes();
}
