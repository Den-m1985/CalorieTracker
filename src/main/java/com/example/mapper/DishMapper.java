package com.example.mapper;

import com.example.model.Dish;
import com.example.model.dto.DishDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DishMapper {

    Dish toEntity(DishDto dto);

    DishDto toDto(Dish entity);

    List<DishDto> toDtoList(List<Dish> entities);
}
