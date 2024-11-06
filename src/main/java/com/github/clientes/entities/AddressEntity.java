package com.github.clientes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_address")
public class AddressEntity extends EntityId{


    @Column(name = "externalUUID", nullable = false)
    private UUID externalUuid;
    @Column (name = "rua", nullable = false)
    private String rua;

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "uf", nullable = false)
    private String uf;

    @CurrentTimestamp
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private AddressEntity addressEntity;


    @PrePersist
    private void generateExternalUuid() {
        this.externalUuid = UUID.randomUUID();
    }

}
