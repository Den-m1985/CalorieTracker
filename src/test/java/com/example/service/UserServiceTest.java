package com.example.service;

import com.example.mapper.UserMapper;
import com.example.model.Contact;
import com.example.model.User;
import com.example.model.dto.UserDto;
import com.example.model.enums.Gender;
import com.example.model.enums.Goal;
import com.example.repository.MealRepository;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private MealRepository mealRepository;

    private UserDto userDto;
    private User user;

    private String firstName = "First name";
    private String middleName = "Middle name";
    private String lastName = "Last name";
    private String email = "john.doe@example.com";
    private String password = "password";
    private int age = 30;
    private double weight = 100.0;
    private double height = 180.0;
    private Goal goal = Goal.MAINTAIN_WEIGHT;
    private Gender gender = Gender.FEMALE;
    private Double dailyCalories = 1975.0;


    @BeforeEach
    public void setUp() {
        mealRepository.deleteAll();
        userRepository.deleteAll();
        userDto = new UserDto(
                null,
                firstName,
                middleName,
                lastName,
                email,
                password,
                age,
                weight,
                height,
                goal,
                gender,
                dailyCalories
        );
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhoneNumber("+71234567890");

        user = userMapper.toEntity(userDto);
        user.setContact(contact);
        user = userService.saveUser(user);
    }

    @Test
    public void shouldCreateUserSuccessfully() {
        userRepository.deleteAll();
        UserDto createdUser = userService.createUser(userDto);
        assertEquals(createdUser.firstName(), firstName);
        assertEquals(createdUser.middleName(), middleName);
        assertEquals(createdUser.lastName(), lastName);
        assertEquals(createdUser.password(), password);
        assertEquals(createdUser.age(), age);
        assertEquals(createdUser.weight(), weight);
        assertEquals(createdUser.height(), height);
        assertEquals(createdUser.goal(), goal);
        assertEquals(createdUser.gender(), gender);
        assertEquals(createdUser.dailyCalories(), dailyCalories);
        assertEquals(createdUser.email(), email);

    }

    @Test
    public void shouldReturnUserById() {
        User resultUser = userService.findUserById(user.getId());
        assertEquals(resultUser.getId(), user.getId());
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        assertThatThrownBy(() -> userService.findUserById(user.getId() + 1))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void getAllUsers() {
        userRepository.deleteAll();
        int countUsers = 10;
        for (int i = 0; i < countUsers; i++) {
            User newUser = new User();
            newUser.setPassword(password);
            newUser.setAge(age);
            newUser.setWeight(weight);
            newUser.setHeight(height);
            newUser.setGender(gender);
            userService.saveUser(newUser);
        }
        List<UserDto> resultUsers = userService.getAllUsers();
        assertEquals(countUsers, resultUsers.size());
    }

    @Test
    public void addNewCalories() {
        user.setWeight(weight + 1);
        userService.saveUser(user);
        userService.recalculateDailyCalories(user.getId());
        user = userService.findUserById(user.getId());
        assertEquals(1985, user.getDailyCalories());
    }

    @Test
    public void calculateDailyCaloriesTest() {
        double result = userService.calculateDailyCalories(user);
        assertEquals(dailyCalories, result);
    }

    @Test
    public void validateUserFieldTest() {
        userRepository.deleteAll();
        userDto = new UserDto(
                null,
                firstName,
                middleName,
                lastName,
                email,
                password,
                null,
                weight,
                height,
                goal,
                gender,
                dailyCalories
        );
        user = userMapper.toEntity(userDto);
        assertThatThrownBy(() -> userService.saveUser(user))
                .isInstanceOf(TransactionSystemException.class);
    }
}
