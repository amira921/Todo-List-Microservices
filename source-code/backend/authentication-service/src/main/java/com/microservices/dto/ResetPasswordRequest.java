package com.microservices.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "Please enter a valid e-mail address")
    @Email(message = "Please enter a valid e-mail address")
    private String email;
}
