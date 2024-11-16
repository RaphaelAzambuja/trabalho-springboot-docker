package com.github.clientes.dto;

import com.github.clientes.entities.user.UserRole;

public record RegisterUserDTO(String login, String senha, UserRole role) {
}
