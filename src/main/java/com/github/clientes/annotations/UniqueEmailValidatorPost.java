package com.github.clientes.annotations;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.clientes.repositories.CustomerRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidatorPost implements ConstraintValidator<UniqueEmailPost, String> {
    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !customerRepository.existsByEmail(email);
    }
}
