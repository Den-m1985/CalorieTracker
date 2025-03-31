package com.example.repository;

import com.example.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {

    List<Meal> findByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    default List<Meal> findMealsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59, 999_999_999);
        return findByCreatedAtBetween(startOfDay, endOfDay);
    }
}
