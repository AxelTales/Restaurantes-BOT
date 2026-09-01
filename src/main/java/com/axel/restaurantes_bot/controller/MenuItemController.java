package com.axel.restaurantes_bot.controller;

import com.axel.restaurantes_bot.dto.MenuItemRequest;
import com.axel.restaurantes_bot.dto.MenuItemResponse;
import com.axel.restaurantes_bot.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public List<MenuItemResponse> getMenuItems(@PathVariable UUID restaurantId) {
        return menuItemService.getMenuItems(restaurantId);
    }

    @PostMapping
    public ResponseEntity<MenuItemResponse> createMenuItem(
            @PathVariable UUID restaurantId,
            @Valid @RequestBody MenuItemRequest request) {
        MenuItemResponse created = menuItemService.createMenuItem(restaurantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{itemId}")
    public MenuItemResponse updateMenuItem(
            @PathVariable UUID restaurantId,
            @PathVariable UUID itemId,
            @Valid @RequestBody MenuItemRequest request) {
        return menuItemService.updateMenuItem(itemId, request);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteMenuItem(
            @PathVariable UUID restaurantId,
            @PathVariable UUID itemId) {
        menuItemService.deleteMenuItem(itemId);
        return ResponseEntity.noContent().build();
    }
}