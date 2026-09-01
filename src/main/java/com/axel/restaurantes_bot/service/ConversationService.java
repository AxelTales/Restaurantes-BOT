package com.axel.restaurantes_bot.service;

import com.axel.restaurantes_bot.model.MenuItem;
import com.axel.restaurantes_bot.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    private final MenuItemRepository menuItemRepository;

    public ConversationService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public String processIncomingMessage(String userPhone, String incomingText) {
        if (incomingText == null) {
            return "No entendí tu mensaje. Escribe *menú* para ver nuestras opciones.";
        }

        String message = incomingText.trim().toLowerCase();

        if (message.contains("hola") || message.contains("buenas") || message.contains("inicio")) {
            return "¡Hola! Bienvenido a Roosters Burgers & Snacks 🍔.\n\n" +
                   "¿En qué te podemos ayudar hoy?\n" +
                   "1️⃣ Escribe *menú* para ver nuestras opciones.\n" +
                   "2️⃣ Escribe *pedir* para levantar una orden.\n" +
                   "3️⃣ Escribe *horario* para conocer nuestras horas de servicio.";
        } 
        
        if (message.contains("menu") || message.contains("menú")) {
            List<MenuItem> items = menuItemRepository.findAll();
            
            if (items.isEmpty()) {
                return "📋 El menú se encuentra en actualización en este momento.";
            }

            StringBuilder menuResponse = new StringBuilder("🍔 *NUESTRO MENÚ* 🍟\n\n");
            for (MenuItem item : items) {
                menuResponse.append("• *")
                            .append(item.getName())
                            .append("* - $")
                            .append(item.getPrice())
                            .append("\n  ")
                            .append(item.getDescription() != null ? item.getDescription() : "")
                            .append("\n\n");
            }
            menuResponse.append("Para pedir algo, escribe *pedir* seguido del producto.");
            return menuResponse.toString();
        } 
        
        if (message.contains("horario") || message.contains("ubicacion") || message.contains("ubicación")) {
            return "📍 *Ubicación:* Av 16 de Septiembre, Coacalco, EDOMEX.\n" +
                   "⏰ *Horario:* Miércoles a Domingo de 2:00 PM a 10:00 PM.";
        } 
        
        return "🤖 No logré reconocer esa opción.\nEscribe *hola* para ver el menú principal o *menú* para ver los platillos disponibles.";
    }
}
