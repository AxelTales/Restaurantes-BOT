package com.axel.restaurantes_bot.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axel.restaurantes_bot.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    Optional<Restaurant> findByWhatsappNumber(String whatsappNumber);
}