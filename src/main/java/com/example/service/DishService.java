package com.example.service;

import com.example.mapper.DishMapper;
import com.example.model.Dish;
import com.example.model.dto.DishDto;
import com.example.repository.DishRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    public DishDto createDish(DishDto dishDto) {
        Dish dish = dishMapper.toEntity(dishDto);
        return dishMapper.toDto(saveDish(dish));
    }

    public Dish saveDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public DishDto getDishById(Integer id) {
        return dishMapper.toDto(findDishById(id));
    }

    public Dish findDishById(Integer dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + dishId + " not found"));
    }

    public List<DishDto> getAllDishes() {
        return dishMapper.toDtoList(dishRepository.findAll());
    }
}
