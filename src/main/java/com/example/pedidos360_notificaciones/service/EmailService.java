package com.example.pedidos360_notificaciones.service;

import com.example.pedidos360_notificaciones.Config.RabbitMQConfig;
import com.example.pedidos360_notificaciones.dto.EmailDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarACola(EmailDTO emailDTO) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, emailDTO);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void procesarCorreoDesdeCola(EmailDTO emailDTO) {
        System.out.println(">>> [RABBITMQ CONSUMIDOR] Mensaje detectado en la cola. Enviando a: " + emailDTO.getTo());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailDTO.getTo());
            message.setSubject(emailDTO.getSubject());
            message.setText(emailDTO.getBody());
            message.setFrom("martinvtellez22@gmail.com");

            mailSender.send(message);
            System.out.println(">>> [GMAIL] ¡Correo entregado exitosamente a " + emailDTO.getTo() + "!");
        } catch (Exception e) {
            System.err.println(">>> [GMAIL ERROR] Falló al enviar por Gmail: " + e.getMessage());
        }
    }
}