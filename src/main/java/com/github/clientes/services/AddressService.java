package com.github.clientes.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.github.clientes.enterprise.AddressLimitExceededException;
import com.github.clientes.entities.CustomerEntity;
import com.github.clientes.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.clientes.dto.CreateAddressDTO;
import com.github.clientes.entities.AddressEntity;
import com.github.clientes.repositories.AddressRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

@Autowired
AddressRepository addressRepository;

@Autowired
CustomerRepository customerRepository;

@Autowired
private ModelMapper modelMapper;


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


    @Transactional
    public AddressEntity createAddress(CreateAddressDTO addressDTO) {

        CustomerEntity customer = customerRepository.findById(addressDTO.customerId())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));

        List<Object[]> addressStats = addressRepository.countDistinctAddressesByCustomerId();

        long totalDistinctAddresses = addressStats.stream()
                .filter(record -> ((Long) record[0]).equals(customer.getId()))
                .mapToLong(record -> (Long) record[1])
                .findFirst()
                .orElse(0);

        if (totalDistinctAddresses >= 5) {
            throw new AddressLimitExceededException("Limite de 5 endereços distintos excedido.");
        }

        AddressEntity newAddress = new AddressEntity();
        newAddress.setRua(addressDTO.rua());
        newAddress.setBairro(addressDTO.bairro());
        newAddress.setCidade(addressDTO.cidade());
        newAddress.setUf(addressDTO.uf());

        newAddress.setCustomer(customer);

        return addressRepository.save(newAddress);
    }


    @jakarta.transaction.Transactional
    public boolean deleteAddress(String externalUuid) {
        try {
            UUID uuid = UUID.fromString(externalUuid);
            AddressEntity address = addressRepository.findByExternalUuid(uuid);

            if (address == null) {
                return false;
            }

            addressRepository.deleteByExternalUuid(uuid);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar excluir o registro de endereço");
        }
    }

    public AddressEntity updateAddress(String externalUuid, CreateAddressDTO createAddressDTO) {
        AddressEntity address = addressRepository.findByExternalUuid(UUID.fromString(externalUuid));

        if (address == null) {
            throw new RuntimeException("Endereço não encontrado");
        }

        modelMapper.map(createAddressDTO, address);

        return addressRepository.save(address);
    }

}


