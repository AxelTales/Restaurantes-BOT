package com.axel.restaurantes_bot.dto;

import java.util.UUID;

public record RestaurantResponse(
    UUID id,
    String name,
    String whatsappNumber,
    String address
) {}