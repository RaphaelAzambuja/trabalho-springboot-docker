package com.github.clientes.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.clientes.entities.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Page<CustomerEntity> findAll(Pageable pageable);
    boolean existsByEmail(String email);
    CustomerEntity findByExternalUuid(UUID externalUuid);
    boolean existsByEmailAndExternalUuidNot(String email, UUID externalUuid);
}
