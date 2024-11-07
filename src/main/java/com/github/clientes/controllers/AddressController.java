package com.github.clientes.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.clientes.dto.CreateAddressDTO;
import com.github.clientes.entities.AddressEntity;
import com.github.clientes.services.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressEntity>> findAllAddresses() {
        List<AddressEntity> addressEntities = addressService.findAllAddress();
        return ResponseEntity.ok(addressEntities);
    }


    @GetMapping("/{externalUuid}")
    public ResponseEntity<AddressEntity> findAddressById(@PathVariable UUID externalUuid) {
        AddressEntity address = addressService.findAddressByExternalUuid(externalUuid);

        if (address == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(address);
    }

    @PostMapping
    public ResponseEntity<AddressEntity> createAddress(@Valid @RequestBody CreateAddressDTO createAddressDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(createAddressDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
