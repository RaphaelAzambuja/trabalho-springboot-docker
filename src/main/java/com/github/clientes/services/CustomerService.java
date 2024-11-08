package com.github.clientes.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.clientes.dto.CreateCustomerDTO;
import com.github.clientes.dto.UpdateCustomerDTO;
import com.github.clientes.entities.CustomerEntity;
import com.github.clientes.repositories.CustomerRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Page<CustomerEntity> findAllCustomers(Pageable pageable) {
        try {
            return customerRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado: " + e.getMessage(), e);
        }
    }

    public CustomerEntity findCustomerByExternalUuid(UUID uuid) {
        return customerRepository.findByExternalUuid(uuid);
    }

    public CustomerEntity createCustomer(CreateCustomerDTO createCustomerDTO) {
        try {
            CustomerEntity newCustomer = new CustomerEntity();
            newCustomer.setNome(createCustomerDTO.nome());
            newCustomer.setSobrenome(createCustomerDTO.sobrenome());
            newCustomer.setEmail(createCustomerDTO.email());
            newCustomer.setSexo(createCustomerDTO.sexo());
            newCustomer.setDataNascimento(createCustomerDTO.dataNascimento());
    
            return customerRepository.save(newCustomer);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados: " + e.getMessage(), e);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException("Dados inválidos: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado: " + e.getMessage(), e);
        }
    }

    public CustomerEntity updateCustomer(String externalUuid, UpdateCustomerDTO updateCustomerDTO) {
        UUID uuid = UUID.fromString(externalUuid);
        
        CustomerEntity customer = customerRepository.findByExternalUuid(uuid);

        if (customer == null) {
            return null;
        }

        if (updateCustomerDTO.nome() != null) {
            customer.setNome(updateCustomerDTO.nome());
        }

        if (updateCustomerDTO.sobrenome() != null) {
            customer.setSobrenome(updateCustomerDTO.sobrenome());
        }

        if (updateCustomerDTO.email() != null) {
            customer.setEmail(updateCustomerDTO.email());
        }

        if (updateCustomerDTO.sexo() != null) {
            customer.setSexo(updateCustomerDTO.sexo());
        }

        if (updateCustomerDTO.dataNascimento() != null) {
            customer.setDataNascimento(updateCustomerDTO.dataNascimento());
        }

        return customerRepository.save(customer);
    }
}
