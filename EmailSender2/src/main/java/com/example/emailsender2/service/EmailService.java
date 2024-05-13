package com.example.emailsender2.service;

import com.example.emailsender2.dtos.EmailUserDTO;
import com.example.emailsender2.entities.Email;
import com.example.emailsender2.enums.StatusEmail;
import com.example.emailsender2.repositories.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {
    private JavaMailSender emailSender;

    private final EmailRepository emailRepository;

    public EmailService(JavaMailSender emailSender, EmailRepository emailRepository) {
        this.emailSender = emailSender;
        this.emailRepository = emailRepository;
    }

    public EmailUserDTO sendEmailUser(EmailUserDTO emailUserDTO) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Set email details
            helper.setFrom(emailUserDTO.getEmailFrom());
            helper.setTo(emailUserDTO.getEmailTo());
            helper.setSubject(emailUserDTO.getTitle());

            // Create the HTML content for the email
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Email Template</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div style=\"font-family: Arial, sans-serif; font-size: 14px;\">\n" +
                    "        <h2 style=\"color: #333;\">Dear " + emailUserDTO.getName() + ",</h2>\n" +
                    "        <p style=\"color: #333;\">You have successfully created your account.</p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";

            // Set email content
            helper.setText(htmlContent, true);

            // Send the email
            emailSender.send(mimeMessage);

            return emailUserDTO;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Email sendEmailRezervare(Email email) {
        email.setEmailDate(LocalDateTime.now());
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Set email details
            helper.setFrom(email.getEmailFrom());
            helper.setTo(email.getEmailTo());
            helper.setSubject(email.getTitle());

            // Create the HTML content for the email using the template
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Email Template</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div style=\"font-family: Arial, sans-serif; font-size: 14px;\">\n" +
                    "    <img src=\"https://images.pexels.com/photos/913215/pexels-photo-913215.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1\" alt=\"Welcome Image\" style=\"display: block; margin: auto;\">\n" +
                    "        <h2 style=\"color: #333;\">Dear " + email.getName() + ",</h2>\n" +
                    "        <p style=\"color: #333;\">Your reservation with the id: " + email.getReservationId() + " was successfully created.</p>\n" +
                    "        <p style=\"color: #333;\">You can see below some information about your trip with us:</p>\n" +
                    "        <ul style=\"color: #333;\">\n" +
                    "            <li><strong>Name:</strong> " + email.getName() + "</li>\n" +
                    "            <li><strong>Email:</strong> " + email.getEmailTo() + "</li>\n" +
                    "            <li><strong>Start Date:</strong> " + email.getStartDate() + "</li>\n" +
                    "            <li><strong>End Date:</strong> " + email.getEndDate() + "</li>\n" +
                    "            <li><strong>Room Numbers:</strong> " + email.getRoomNumbers() + "</li>\n" +
                    "            <li><strong>Final Cost:</strong> " + email.getReservationCost() + "</li>\n" +
                    "        </ul>\n" +
                    "        <p style=\"color: #333;\">Thank you for choosing us! See you soon!</p>\n" +
                    "        <p style=\"color: #333;\">Best Regards,<br/>" + email.getOwnerRef() + "</p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";

            // Set email content
            helper.setText(htmlContent, true);

            // Send the email
            emailSender.send(mimeMessage);

            // Update email status
            email.setEmailStatus(StatusEmail.SENT);
        } catch (MessagingException e) {
            // Handle messaging exception
            email.setEmailStatus(StatusEmail.ERROR);
            throw new RuntimeException(e);
        } catch (MailException e) {
            // Handle mail exception
            email.setEmailStatus(StatusEmail.ERROR);
        }

        // Save and return the email
        return emailRepository.save(email);
    }
}
