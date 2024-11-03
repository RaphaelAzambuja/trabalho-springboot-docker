package com.github.clientes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.clientes.entities.CustomerEntity;
import java.util.UUID;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmail(String email);
    CustomerEntity findByExternalUuid(UUID externalUuid);
}
