package com.github.clientes.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.github.clientes.enums.Sex;

public record GetCustomerDTO(
    UUID externalUuid,
    String nome,
    String sobrenome,
    String email,
    Sex sexo,
    LocalDate dataNascimento
) {}
