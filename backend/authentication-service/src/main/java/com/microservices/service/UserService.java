package com.microservices.service;

import com.microservices.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService{
    // Registration Functions
    RegistrationRequest register(RegistrationRequest newAccount) throws Exception;
    boolean isEmailExist(String userEmail);
    RegistrationRequest createAccount(RegistrationRequest newAccount);
    boolean saveAccount(RegistrationRequest newUser);
    void sendVerificationMail(String email);
    void updateAccountIfEmailSent(RegistrationRequest updatedAccount);

    // Account Verification
    void activateAccount(String email);

    // Authentication Functions
    String authenticate(AuthRequest request);

    // Reset Password Functions
    boolean sendResetPasswordMail(String email);
    void resetPassword(AuthRequest request);

    String getResetPasswordForm(String token);
}
