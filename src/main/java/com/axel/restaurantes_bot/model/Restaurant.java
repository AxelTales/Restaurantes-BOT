package com.axel.restaurantes_bot.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "whatsapp_number", nullable = false, unique = true)
    private String whatsappNumber;

    private String address;

    @Column(name = "created_at", updatable = false, insertable = false)
    private OffsetDateTime createdAt;
}