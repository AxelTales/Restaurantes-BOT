package com.axel.restaurantes_bot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.axel.restaurantes_bot.dto.RestaurantResponse;
import com.axel.restaurantes_bot.model.Restaurant;
import com.axel.restaurantes_bot.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    // Inyección de dependencias por constructor
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private RestaurantResponse toResponse(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getWhatsappNumber(),
                restaurant.getAddress()
        );
    }
}