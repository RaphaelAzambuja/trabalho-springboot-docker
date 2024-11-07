package com.github.clientes.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_customers")
public class CustomerEntity extends EntityId {
    @Column(name = "externalUuid", nullable = false)
    private UUID externalUuid;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "sobrenome", nullable = false, length = 255)
    private String sobrenome;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "sexo", length = 10)
    private String sexo;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "data_cadastro", nullable = false)
    @CurrentTimestamp
    private LocalDateTime dataCadastro;

    @PrePersist
    private void generateExternalUuid() {
        this.externalUuid = UUID.randomUUID();
    }
}
