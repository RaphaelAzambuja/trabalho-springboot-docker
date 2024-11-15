package com.github.clientes.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.clientes.entities.AddressEntity;

@Repository
public interface AddressRepository extends JpaRepository <AddressEntity, Long> {

    AddressEntity findByExternalUuid(UUID externalUuid);

    @Query(
        """
        SELECT COUNT(CONCAT(address.rua, address.bairro, address.cidade, address.uf))
        FROM AddressEntity address
        INNER JOIN CustomerEntity customer ON address.customer.id = customer.id
        WHERE customer.id = :customerId       
        """
    )
    long countDistinctAddressesByCustomerId(@Param("customerId") long customerId);

    @Modifying
    @Transactional
    void deleteByExternalUuid(UUID externalUuid);
}
