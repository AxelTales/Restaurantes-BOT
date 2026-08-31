package com.axel.restaurantes_bot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "conversations")
@Getter
@Setter
public class Conversation {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    @Column(nullable = false)
    private String state = "INICIO";

    // El tipo JSONB de Postgres lo mapeamos como String por ahora (simple)
    // y lo convertimos a/desde JSON manualmente en el Service.
    @Column(columnDefinition = "jsonb")
    private String context;

    @Column(name = "updated_at", insertable = false)
    private OffsetDateTime updatedAt;
}