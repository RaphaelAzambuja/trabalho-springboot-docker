package com.github.clientes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.clientes.dto.CreateCustomerDTO;
import com.github.clientes.dto.UpdateCustomerDTO;
import com.github.clientes.entities.CustomerEntity;
import com.github.clientes.services.CustomerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<Page<CustomerEntity>> findAllCustomers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,
        @RequestParam(defaultValue = "id") String orderBy,
        @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable  = PageRequest.of(page, size, Sort.by(direction, orderBy));
        
        Page<CustomerEntity> customers = customerService.findAllCustomers(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @GetMapping("/{externalUuid}")
    public ResponseEntity<CustomerEntity> findCustomerById(@PathVariable String externalUuid) {
        CustomerEntity customer = customerService.findCustomerByExternalUuid(externalUuid);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerEntity> createCustomer(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(createCustomerDTO));
    }

    @PatchMapping("/{externalUuid}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable String externalUuid, @Valid @RequestBody UpdateCustomerDTO updateCustomerDTO) {
        CustomerEntity updatedCustomer = customerService.updateCustomer(externalUuid, updateCustomerDTO);

        if (updatedCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

    @DeleteMapping("/{externalUuid}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String externalUuid) {
        try {
            boolean deleted = customerService.deleteCustomer(externalUuid);
    
            if (deleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Cliente excluído com sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o cliente: " + e.getMessage());
        }
    }
}
