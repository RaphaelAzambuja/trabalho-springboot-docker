package com.github.clientes.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.clientes.dto.CreateCustomerDTO;
import com.github.clientes.dto.GetCustomerDTO;
import com.github.clientes.dto.UpdateCustomerDTO;
import com.github.clientes.entities.CustomerEntity;
import com.github.clientes.repositories.CustomerRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Page<GetCustomerDTO> findAllCustomers(Pageable pageable) {
        try {
            Page<CustomerEntity> customers = customerRepository.findAll(pageable);

            return customers.map(customer -> customer.toDTO());
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado: " + e.getMessage(), e);
        }
    }

    public GetCustomerDTO findCustomerByExternalUuid(String externalUuid) {
        CustomerEntity customer = customerRepository.findByExternalUuid(UUID.fromString(externalUuid));
        
        return customer != null ? customer.toDTO() : null;
    }

    public GetCustomerDTO createCustomer(CreateCustomerDTO createCustomerDTO) {
        try {
            CustomerEntity newCustomer = new CustomerEntity();
            newCustomer.setNome(createCustomerDTO.nome());
            newCustomer.setSobrenome(createCustomerDTO.sobrenome());
            newCustomer.setEmail(createCustomerDTO.email());
            newCustomer.setSexo(createCustomerDTO.sexo());
            newCustomer.setDataNascimento(createCustomerDTO.dataNascimento());
    
            CustomerEntity customer = customerRepository.save(newCustomer);

            return customer.toDTO();

        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados: " + e.getMessage(), e);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException("Dados inválidos: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado: " + e.getMessage(), e);
        }
    }

    public GetCustomerDTO updateCustomer(String externalUuid, UpdateCustomerDTO updateCustomerDTO) {
        CustomerEntity customer = customerRepository.findByExternalUuid(UUID.fromString(externalUuid));

        if (customer == null) {
            return null;
        }

        customer.setNome(updateCustomerDTO.nome());
        customer.setSobrenome(updateCustomerDTO.sobrenome());
        customer.setEmail(updateCustomerDTO.email());
        customer.setSexo(updateCustomerDTO.sexo());
        customer.setDataNascimento(updateCustomerDTO.dataNascimento());

        return customerRepository.save(customer).toDTO();
    }

    @Transactional
    public boolean deleteCustomer(String externalUuid) {
        try {
            UUID uuid = UUID.fromString(externalUuid);
            CustomerEntity customer = customerRepository.findByExternalUuid(uuid);

            if (customer == null) {
                return false;
            }

            customerRepository.deleteByExternalUuid(uuid);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar excluir o cliente");
        }
    }
}
