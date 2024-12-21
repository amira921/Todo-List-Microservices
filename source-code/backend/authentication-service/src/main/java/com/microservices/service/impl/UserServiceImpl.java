package com.microservices.service.impl;

import com.microservices.dto.*;
import com.microservices.exception.AuthException;
import com.microservices.mapper.UserMapper;
import com.microservices.model.User;
import com.microservices.repository.UserRepository;
import com.microservices.security.*;
import com.microservices.service.*;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final JwtUserDetailsService userDetailsService;
    private final JwtUtilities jwtUtilities;
    private final EmailService emailService;


    @Override
    public RegistrationRequest register(RegistrationRequest newAccount) throws Exception {
        log.info("Registration Request is running...");
        RegistrationRequest account = null;
        if (!isEmailExist(newAccount.getEmail())) {
            account = createAccount(newAccount);
            saveAccount(account);
            sendVerificationMail(account.getEmail());
            updateAccountIfEmailSent(account);

            log.info("Registration request accepted");
        } else{
            log.error("Registration request failed");
            AuthException error = AuthException.builder().message("Account Already Exists").build();
            throw error;
        }
        return account;
    }

    @Override
    public void activateAccount(String email) {
        log.info("Account Activation is running....");
        User user = repository.findByEmail(email);
        if (user != null) {
            user.setAccountActive(true);
            repository.save(user);
            log.info("User Account is activated");
        }
    }

    @Override
    public String authenticate(AuthRequest request) {
        log.info("Authentication request is sent...");

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        if (userDetails == null) {
            log.info("Account not found, user needs to register");
            return "notFound";
        } else if (!userDetails.isEnabled()) {
            log.info("Account is inactive");
            return "inactive";
        } else {
            String actualPassword = userDetails.getPassword();
            boolean passwordMatches = encoder.matches(request.getPassword(), actualPassword);
            if (passwordMatches) {
                String jwtToken = jwtUtilities.generateToken(userDetails.getUsername());
                log.info("User authenticated successfully!");
                return jwtToken;
            } else {
                log.info("Password is incorrect");
                return "incorrectPassword";
            }
        }
    }

    @Override
    public boolean sendResetPasswordMail(String email) {
        if(isEmailExist(email)){
            String resetToken = jwtUtilities.generateToken(email);
            boolean isSent = emailService.sendResetPasswordEmail(email,resetToken);
            if(isSent){
                log.info("Reset mail sent!");
                return true;
            }
        }
        log.info("User not found!");
        return false;
    }

    @Override
    public void resetPassword(AuthRequest request) {
        User user = repository.findByEmail(request.getEmail());
        if(user != null){
            user.setPassword(encoder.encode(request.getPassword()));
            repository.save(user);
            log.info("password changed successfully!");
        }else{
            log.info("user not found!");
        }

    }

    @Override
    public String getResetPasswordForm(String token) {
        try{
            if(!jwtUtilities.isTokenExpired(token)) {
                return "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "</head>"
                        + "<body>"
                        + "<h2>Reset Password</h2>"
                        + "<form action=\"/auth/reset-password/save\" method=\"post\">"
                        + "<label for=\"email\">Enter Email:</label><br>"
                        + "<input type=\"email\" id=\"email\" name=\"email\"><br>"
                        + "<label for=\"password\">Enter New Password:</label><br>"
                        + "<input type=\"password\" id=\"password\" name=\"password\"><br><br>"
                        + "<input type=\"submit\" value=\"Reset\">"
                        + "</form>"
                        + "</body>"
                        + "</html>";
            }
        }catch (ExpiredJwtException e){
            log.error(e.getMessage());
        }
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "</head>"
                + "<body>"
                + "<h3>Token Expired, try again</h3>"
                + "</body>"
                + "</html>";
    }

    @Override
    public boolean isEmailExist(String userEmail) {
        return (userDetailsService.loadUserByUsername(userEmail) != null) ? true : false;
    }

    @Override
    public RegistrationRequest createAccount(RegistrationRequest newAccount) {
        newAccount.setPassword(encoder.encode(newAccount.getPassword()));
        newAccount.setCreated_at(new Date());
        log.info("New Account prepared to save in database");
        return newAccount;
    }

    @Override
    public boolean saveAccount(RegistrationRequest newUser) {
        User user = mapper.mapToEntity(newUser);
        user = repository.save(user);
        if (user != null) {
            log.info("New Account saved in database");
            return true;
        }
        log.error("New Account isn't saved in database");
        return false;
    }

    @Override
    public void sendVerificationMail(String email) {
        String activationToken = jwtUtilities.generateToken(email);
        boolean isSent = emailService.sendActivationEmail(email, activationToken);
        if(isSent) log.info("Activation mail sent!");
        else log.info("activation mail failed to sent!");
    }

    @Override
    public void updateAccountIfEmailSent(RegistrationRequest updatedAccount) {
        User user = repository.findByEmail(updatedAccount.getEmail());
        if (user != null) {
            user.setActivationEmailSent(true);
            repository.save(user);
            log.info("Account updated in database");
        } else log.error("Account isn't updated in database");
    }
}
