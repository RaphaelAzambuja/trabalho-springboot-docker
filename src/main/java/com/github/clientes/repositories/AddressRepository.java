package com.github.clientes.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.clientes.entities.AddressEntity;

@Repository
public interface AddressRepository extends JpaRepository <AddressEntity, Long> {

    AddressEntity findByExternalUuid(UUID externalUuid);

    @Query("SELECT c.id AS clienteId, COUNT(DISTINCT CONCAT(a.rua, a.bairro, a.cidade, a.uf)) AS totalCadastros " +
            "FROM AddressEntity a " +
            "INNER JOIN CustomerEntity c ON a.customer.id = c.id " +
            "GROUP BY c.id " +
            "ORDER BY totalCadastros DESC")
    List<Object[]> countDistinctAddressesByCustomerId();

    @Modifying
    @Transactional
    void deleteByExternalUuid(UUID externalUuid);
}




