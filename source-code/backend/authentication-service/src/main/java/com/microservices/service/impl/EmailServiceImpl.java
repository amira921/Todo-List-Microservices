package com.microservices.service.impl;

import com.microservices.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.*;
import org.springframework.mail.javamail.JavaMailSender;
import static jakarta.mail.Message.RecipientType.TO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender sender){
        this.mailSender=sender;
    }

    @Override
    public boolean sendActivationEmail(String userEmail, String activationToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setRecipient(TO, new InternetAddress(userEmail));
            message.setFrom(new InternetAddress(from));
            message.setSubject("TodoList Website - Activate Your Account");
            String url = "http://localhost:8080/auth/register/verify/" + userEmail + "/" + activationToken;
            String content = "Navigate the url " + url;
            message.setText(content);
            mailSender.send(message);
            log.info("Activation Message send successfully");
            return true;
        } catch (MessagingException e) {
            log.error(e.getMessage());
            return false;
        }
    }


    @Override
    public boolean sendResetPasswordEmail(String email, String token){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            message.setRecipient(TO, new InternetAddress(email));
            message.setFrom(new InternetAddress(from));
            message.setSubject("TodoList Website - Reset Password");
            String url = "http://localhost:8080/auth/reset-password/" + email + "/" + token;
            String content = "Navigate the url " + url;
            message.setText(content);
            mailSender.send(message);
            return true;
        }catch (MessagingException ex){
            log.error(ex.getMessage());
            return false;
        }
    }
}
