package com.example.simpleweb.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeeDto {
    @NotBlank(message = "不得為空白")
    @Size(min = 2, message = "最少需要2個字元")
    private String firstName;

    @NotBlank(message = "不得為空白")
    @Size(min = 2, message = "最少需要2個字元")
    private String lastName;

    @NotBlank(message = "不得為空白")
    @Email(message = "需為email格式")
    private String email;

    @NotBlank(message = "不得為空白")
    @Pattern(regexp = "^[A-Za-z /.\\s-]+$", message = "不得包含數字")
    private String role;
}
