package com.github.clientes.dto;

import java.time.LocalDate;

import com.github.clientes.annotations.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public record UpdateCustomerDTO(
    @NotNull(message = "O nome não pode estar nulo")
    @NotBlank(message = "O nome não pode estar vazio")
    String nome,

    @NotNull(message = "O sobrenome não pode estar nulo")
    @NotBlank(message = "O sobrenome não pode estar vazio")
    String sobrenome,

    @NotNull(message = "O email não pode estar nulo")
    @NotBlank(message = "O email não pode estar vazio")
    @Email(message = "O email deve ser válido")
    @UniqueEmail
    String email,

    @NotNull(message = "O sexo não pode estar nulo")
    @NotBlank(message = "O sexo não pode estar vazio")
    @Pattern(regexp = "^(Masculino|Feminino|Outro)$", message = "O sexo deve ser 'Masculino', 'Feminino' ou 'Outro'")
    String sexo,

    @NotNull(message = "A data de nascimento não pode estar nula")
    @Past(message = "A data de nascimento deve ser uma data passada")
    LocalDate dataNascimento
) {}
