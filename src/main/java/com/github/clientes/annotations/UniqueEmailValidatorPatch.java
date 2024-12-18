package com.github.clientes.annotations;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.clientes.repositories.CustomerRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidatorPatch implements ConstraintValidator<UniqueEmailPatch, String> {
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        String requestURI = request.getRequestURI();
        String externalUuid = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        
        if (externalUuid != null && !externalUuid.trim().isEmpty()) {
            UUID uuid = UUID.fromString(externalUuid);
            
            if (uuid != null) {
                var existingCustomer = customerRepository.findByExternalUuid(uuid);
                
                if (existingCustomer != null && existingCustomer.getEmail().equals(email)) {
                    return true;
                }
            }
        }

        return !customerRepository.existsByEmail(email);
    }
}
