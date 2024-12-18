package com.microservices.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Integer id;
    private String userEmail;

    @NotBlank(message = "Please enter a title")
    private String title;

    @NotBlank(message = "Please enter a description")
    private String description;

    @NotBlank(message = "Please choose a category (Personal or Work)")
    private String category;

    @NotNull(message = "Please enter a due date for the task")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dueDate;

    private String status;
}
