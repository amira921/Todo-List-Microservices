package com.microservices.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest{
    @NotBlank(message = "Please enter a valid e-mail address")
    @Email(message = "Please enter a valid e-mail address")
    private String email;

    @NotBlank(message = "Please enter a valid password")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, " +
                    "one uppercase letter, one special character, and be at least 8 characters long"
    )
    private String password;

    @NotBlank(message = "Please enter a valid username")
    private String username;

    private Date created_at;
    private boolean isActivationEmailSent;
    private boolean isAccountActive;}
