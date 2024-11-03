package com.github.clientes.annotations;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.clientes.repositories.CustomerRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }
        return !customerRepository.existsByEmail(email);
    }
}
