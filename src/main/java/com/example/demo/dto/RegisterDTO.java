package com.example.demo.dto;

import com.example.demo.models.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
