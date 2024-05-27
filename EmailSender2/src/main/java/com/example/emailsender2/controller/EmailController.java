package com.example.emailsender2.controller;

import com.example.emailsender2.dtos.EmailUserDTO;
import com.example.emailsender2.entities.Email;
import com.example.emailsender2.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/emails")
public class EmailController {

    public static final String FIRST_TOKEN = "6d91f453-b188-4dda-b320-3af63b1c4d01"; // admin id
    public static final String SECOND_TOKEN = "42b00d5f-6a77-4c46-ba35-368a68131e4a"; // client id

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    Logger logger = LogManager.getLogger(EmailController.class);

    @PostMapping("/sending-email")
    public ResponseEntity<Boolean> sendingEmail(
            @RequestPart("emailRequest") Email email,
            @RequestPart("file") MultipartFile file) {
        emailService.sendEmailRezervare1(email, file);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
