package com.example.controller;

import com.example.model.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "User Controller", description = "Управление пользователями")
public interface UserApi {

    @Operation(summary = "Создать пользователя", description = "Добавляет нового пользователя в систему.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
    })
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto);

    @Operation(summary = "Получить пользователя по ID", description = "Возвращает данные пользователя по указанному ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные пользователя успешно получены"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    ResponseEntity<UserDto> getUser(@PathVariable Integer id);

    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех пользователей.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен")
    })
    public ResponseEntity<List<UserDto>> getAllUsers();

}
