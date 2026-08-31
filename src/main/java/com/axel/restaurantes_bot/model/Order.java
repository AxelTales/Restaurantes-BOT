package com.axel.restaurantes_bot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    @Column(nullable = false, columnDefinition = "jsonb")
    private String items;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private String status = "PENDIENTE";

    @Column(name = "created_at", updatable = false, insertable = false)
    private OffsetDateTime createdAt;
}