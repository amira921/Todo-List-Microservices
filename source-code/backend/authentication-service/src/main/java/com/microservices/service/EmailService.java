package com.microservices.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    boolean sendActivationEmail(String userEmail, String activationToken);
    boolean sendResetPasswordEmail(String email, String token);
}
