package com.microservices.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ValidationError {
    private String field;
    private String message;
}