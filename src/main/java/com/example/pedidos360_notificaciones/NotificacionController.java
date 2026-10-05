package com.example.pedidos360_notificaciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @PostMapping("/enviar")
    public ResponseEntity<Map<String, Object>> enviarNotificacion(@RequestBody Map<String, String> payload) {
        Map<String, Object> respuesta = new HashMap<>();

        String destinatario = payload.get("destinatario");
        String mensaje = payload.get("mensaje");
        String tipo = payload.getOrDefault("tipo", "EMAIL");

        if (destinatario == null || destinatario.isBlank() || mensaje == null || mensaje.isBlank()) {
            respuesta.put("estado", "ERROR");
            respuesta.put("mensaje", "Los campos 'destinatario' y 'mensaje' son obligatorios");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }

        respuesta.put("idNotificacion", UUID.randomUUID().toString().substring(0, 8));
        respuesta.put("estado", "ENVIADO");
        respuesta.put("tipo", tipo.toUpperCase());
        respuesta.put("destinatario", destinatario);
        respuesta.put("mensajeDetalle", "Notificación enviada correctamente");

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/salud")
    public ResponseEntity<String> verificarEstado() {
        return ResponseEntity.ok("Servicio de Notificaciones Operativo");
    }
}