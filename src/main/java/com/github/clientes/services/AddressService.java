package com.github.clientes.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.clientes.dto.CreateAddressDTO;
import com.github.clientes.entities.AddressEntity;
import com.github.clientes.repositories.AddressRepository;

@Service
public class AddressService {

@Autowired
AddressRepository addressRepository;


    public List<AddressEntity> findAllAddress() {
        List<AddressEntity> addresses = addressRepository.findAll();

        if (addresses.isEmpty()) {
            throw new NoSuchElementException("Nenhum endereço encontrado.");
        }

        for (AddressEntity address : addresses) {
            if (address.getRua() == null || address.getRua().isEmpty() ||
                    address.getBairro() == null || address.getBairro().isEmpty() ||
                    address.getCidade() == null || address.getCidade().isEmpty() ||
                    address.getUf() == null || address.getUf().isEmpty()) {
                throw new IllegalArgumentException("Endereço com campos obrigatórios vazios encontrado.");
            }
        }

        return addresses;
    }


    public AddressEntity findAddressByExternalUuid(UUID uuid) {
        return addressRepository.findByExternalUuid(uuid);
    }


    public AddressEntity createAddress(CreateAddressDTO addressDTO) {
        AddressEntity newAddress = new AddressEntity();
        newAddress.setRua(addressDTO.rua());
        newAddress.setBairro(addressDTO.bairro());
        newAddress.setCidade(addressDTO.cidade());
        newAddress.setUf(addressDTO.uf());

        return addressRepository.save(newAddress);
    }


}
