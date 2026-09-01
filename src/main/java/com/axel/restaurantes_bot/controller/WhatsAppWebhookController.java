package com.axel.restaurantes_bot.controller;

import com.axel.restaurantes_bot.service.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/whatsapp/webhook")
public class WhatsAppWebhookController {

    private static final String VERIFY_TOKEN = "mi_token_secreto_restaurante";
    private final ConversationService conversationService;

    public WhatsAppWebhookController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam(name = "hub.mode", required = false) String mode,
            @RequestParam(name = "hub.verify_token", required = false) String token,
            @RequestParam(name = "hub.challenge", required = false) String challenge) {

        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token de verificación inválido");
    }

    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody Map<String, Object> payload) {
        String sender = (String) payload.getOrDefault("sender", "anonimo");
        String message = (String) payload.getOrDefault("message", "");

        // Procesa la lógica y obtiene la respuesta del bot
        String botReply = conversationService.processIncomingMessage(sender, message);

        System.out.println("----------------------------------------");
        System.out.println("De: " + sender);
        System.out.println("Mensaje recibido: " + message);
        System.out.println("Respuesta del bot:\n" + botReply);
        System.out.println("----------------------------------------");

        return ResponseEntity.ok(botReply);
    }
}