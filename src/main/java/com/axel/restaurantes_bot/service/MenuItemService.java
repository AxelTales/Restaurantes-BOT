package com.axel.restaurantes_bot.service;

import com.axel.restaurantes_bot.dto.MenuItemRequest;
import com.axel.restaurantes_bot.dto.MenuItemResponse;
import com.axel.restaurantes_bot.exception.ResourceNotFoundException;
import com.axel.restaurantes_bot.model.MenuItem;
import com.axel.restaurantes_bot.model.Restaurant;
import com.axel.restaurantes_bot.repository.MenuItemRepository;
import com.axel.restaurantes_bot.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemService(MenuItemRepository menuItemRepository,
                            RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // READ - listar todos los items de un restaurante
    public List<MenuItemResponse> getMenuItems(UUID restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // CREATE
    public MenuItemResponse createMenuItem(UUID restaurantId, MenuItemRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un restaurante con id " + restaurantId));

        MenuItem item = new MenuItem();
        item.setRestaurant(restaurant);
        item.setName(request.name());
        item.setDescription(request.description());
        item.setPrice(request.price());
        item.setAvailable(request.available() != null ? request.available() : true);

        MenuItem saved = menuItemRepository.save(item);
        return toResponse(saved);
    }

    // UPDATE
    public MenuItemResponse updateMenuItem(UUID itemId, MenuItemRequest request) {
        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un producto con id " + itemId));

        item.setName(request.name());
        item.setDescription(request.description());
        item.setPrice(request.price());
        if (request.available() != null) {
            item.setAvailable(request.available());
        }

        MenuItem updated = menuItemRepository.save(item);
        return toResponse(updated);
    }

    // DELETE
    public void deleteMenuItem(UUID itemId) {
        if (!menuItemRepository.existsById(itemId)) {
            throw new ResourceNotFoundException("No existe un producto con id " + itemId);
        }
        menuItemRepository.deleteById(itemId);
    }

    private MenuItemResponse toResponse(MenuItem item) {
        return new MenuItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAvailable()
        );
    }
}