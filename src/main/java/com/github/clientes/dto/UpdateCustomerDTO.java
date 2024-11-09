package com.github.clientes.dto;

import java.time.LocalDate;

import com.github.clientes.annotations.UniqueEmailPatch;
import com.github.clientes.enums.Sex;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

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
    @UniqueEmailPatch
    String email,

    Sex sexo,

    @NotNull(message = "A data de nascimento não pode estar nula")
    @Past(message = "A data de nascimento deve ser uma data passada")
    LocalDate dataNascimento
) {
    public UpdateCustomerDTO {
        if (sexo == null) {
            sexo = Sex.NAO_INFORMADO;
        }
    }
}
