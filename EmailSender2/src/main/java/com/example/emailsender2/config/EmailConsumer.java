package com.example.emailsender2.config;


import com.example.emailsender2.dtos.EmailDTO;
import com.example.emailsender2.entities.Email;
import com.example.emailsender2.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {
    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "emailQueue")
    public void consumeMessage(EmailDTO emailDTO){
        Email email = new Email();
        BeanUtils.copyProperties(emailDTO, email);
        emailService.sendEmailRezervare(email);
        System.out.println("Email Status: " + email.getEmailStatus().toString());

    }
}