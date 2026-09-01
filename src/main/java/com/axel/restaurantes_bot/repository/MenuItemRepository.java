package com.axel.restaurantes_bot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axel.restaurantes_bot.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
    List<MenuItem> findByRestaurantId(UUID restaurantId);
}