package com.axel.restaurantes_bot.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record MenuItemRequest(
    @NotBlank(message = "El nombre no puede estar vacío")
    String name,

    String description,

    @Positive(message = "El precio debe ser mayor a $0")
    BigDecimal price,

    Boolean available
) {}