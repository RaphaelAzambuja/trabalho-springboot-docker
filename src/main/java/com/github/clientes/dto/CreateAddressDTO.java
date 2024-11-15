package com.github.clientes.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAddressDTO (
        @NotNull(message = "A informação rua não pode ser nula")
        @NotBlank(message = "A informação rua não pode estar vazia")
        String rua,

        @NotNull(message = "A informação bairro não pode ser nula")
        @NotBlank(message = "A informação bairro não pode estar vazia")
        String bairro,

        @NotNull(message = "A informação cidade não pode ser nula")
        @NotBlank(message = "A informação cidade não pode estar vazia")
        String cidade,

        @NotNull(message = "A informação estado da federação não pode ser nula")
        @NotBlank(message = "A informação estado da federação não pode estar vazia")
        String uf,

        @NotNull(message = "O ID do cliente não pode ser nulo")
        UUID customerUuid
) {}
