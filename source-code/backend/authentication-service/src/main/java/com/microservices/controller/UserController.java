package com.microservices.controller;

import com.microservices.dto.*;
import com.microservices.exception.AuthException;
import com.microservices.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest newRequest){
        Map<String, Object> response = new HashMap<>();
        try {
            RegistrationRequest request = userService.register(newRequest);
            response.put("status", "success");
            response.put("message", "Your account has been successfully created with the email: " + request.getEmail());
            response.put("instructions", "To activate your account, please check your email inbox to verify your email address.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (AuthException e) {
            response.put("status", "error");
            response.put("message", "Account already exists.");
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        }
        catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Unknown error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/register/verify/{email}/{token}")
    public ResponseEntity<?> verifyAccount(@PathVariable("email") String email) {
        userService.activateAccount(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Account Activated Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        String response = userService.authenticate(authRequest);
        switch (response) {
            case "inactive": return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Account with " + authRequest.getEmail() + " is inactive, please check your inbox for activation");

            case "notFound": return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account with " + authRequest.getEmail() + " is not found, please register first!");

            case "incorrectPassword": return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Password is incorrect, please try again!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        boolean isFound = userService.sendResetPasswordMail(request.getEmail());
        return (isFound) ? ResponseEntity.status(HttpStatus.OK).body("Reset mail request is sent, check your inbox!")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
    }

    @GetMapping("/reset-password/{email}/{token}")
    public ResponseEntity<?> showResetPasswordPage(@PathVariable String email,
                                                   @PathVariable String token) {
        String htmlContent = userService.getResetPasswordForm(token);
        return ResponseEntity.status(HttpStatus.OK).body(htmlContent);
    }

    @PostMapping("/reset-password/save")
    public ResponseEntity<?> resetPassword(@Valid @ModelAttribute AuthRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.status(HttpStatus.OK).body("Password Changed Successfully!");
    }

}
