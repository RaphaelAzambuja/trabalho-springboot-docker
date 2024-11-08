package com.github.clientes.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<List<CustomerEntity>> findAllCustomers() {
        List<CustomerEntity> customers = customerService.findAllCustomers();

        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @GetMapping("/{externalUuid}")
    public ResponseEntity<CustomerEntity> findCustomerById(@PathVariable UUID externalUuid) {
        CustomerEntity customer = customerService.findCustomerByExternalUuid(externalUuid);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerEntity> createCustomer(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(createCustomerDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/{externalUuid}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable String externalUuid, @Valid @RequestBody UpdateCustomerDTO updateCustomerDTO) {
        try {
            CustomerEntity updatedCustomer = customerService.updateCustomer(externalUuid, updateCustomerDTO);

            if (updatedCustomer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
