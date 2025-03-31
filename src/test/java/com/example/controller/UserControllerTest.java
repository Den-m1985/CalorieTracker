package com.example.controller;

import com.example.model.dto.UserDto;
import com.example.model.enums.Gender;
import com.example.model.enums.Goal;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Для преобразования объектов в JSON

    @MockitoBean
    private UserService userService;

    private final String BASE_URL = "/users";

    private final UserDto userDto = new UserDto(
            1,
            "John",
            "Middle",
            "Doe",
            "john.doe@example.com",
            "password",
            30,
            80.0,
            180.0,
            Goal.MAINTAIN_WEIGHT,
            Gender.MALE,
            2000.0
    );

    @Test
    public void shouldCreateUserSuccessfully() throws Exception {
        Mockito.when(userService.createUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(userDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.lastName()))
                .andExpect(jsonPath("$.email").value(userDto.email()));
    }

    @Test
    public void shouldGetUserByIdSuccessfully() throws Exception {
        Mockito.when(userService.getUserById(eq(1))).thenReturn(userDto);

        mockMvc.perform(get(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.id()))
                .andExpect(jsonPath("$.email").value(userDto.email()));
    }


    @Test
    public void shouldGetAllUsersSuccessfully() throws Exception {
        List<UserDto> users = List.of(userDto);
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()))
                .andExpect(jsonPath("$[0].email").value(userDto.email()));
    }
}
