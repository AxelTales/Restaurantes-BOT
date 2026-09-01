package com.axel.restaurantes_bot.repository;

import com.axel.restaurantes_bot.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID; // O Long / Integer, según el tipo de ID que use tu modelo MenuItem

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
    // Si tu ID en MenuItem es Long o String, cambia UUID por Long o String
}