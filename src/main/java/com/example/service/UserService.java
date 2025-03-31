package com.example.service;

import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.model.dto.UserDto;
import com.example.model.enums.Goal;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setDailyCalories(calculateDailyCalories(user));
        return userMapper.toDto(saveUser(user));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public UserDto getUserById(Integer id) {
        return userMapper.toDto(findUserById(id));
    }

    public User findUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
    }

    public List<UserDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    public void recalculateDailyCalories(Integer userId) {
        User user = findUserById(userId);
        user.setDailyCalories(calculateDailyCalories(user));
        saveUser(user);
    }

    public Double calculateDailyCalories(User user) {
        return 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() +
                (user.getGoal() == Goal.WEIGHT_LOSS ? -500 : user.getGoal() == Goal.WEIGHT_GAIN ? 500 : 0);
    }

}
