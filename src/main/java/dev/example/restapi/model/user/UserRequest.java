package dev.example.restapi.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotBlank(message = "User name can't be left empty")
        String name,
        @Email(message = "Please enter a valid email Id")
        @NotNull(message = "Email cannot be NULL")
        String email,
        @NotNull(message = "UserType cannot be NULL")
        String userType) {
}
