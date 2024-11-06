package com.github.clientes.repositories;

import com.github.clientes.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository <AddressEntity, Long> {

    AddressEntity findByExternalUuid(UUID externalUuid);

}
