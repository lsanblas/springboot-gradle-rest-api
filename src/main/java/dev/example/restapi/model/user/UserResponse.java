package dev.example.restapi.model.user;

import java.time.LocalDateTime;

public record UserResponse(String id, String name, String email, LocalDateTime createdDate, String userType) {
}
