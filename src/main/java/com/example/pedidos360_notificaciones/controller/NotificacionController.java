package com.example.pedidos360_notificaciones.controller;

import com.example.pedidos360_notificaciones.dto.EmailDTO;
import com.example.pedidos360_notificaciones.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public String enviarNotificacionDirecta(@RequestBody EmailDTO emailDTO) {
        try {
            emailService.enviarACola(emailDTO);
            return "¡Solicitud recibida y encolada en RabbitMQ para " + emailDTO.getTo() + "!";
        } catch (Exception e) {
            return "Error al encolar la notificación: " + e.getMessage();
        }
    }
}