package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.AddressDto;
import com.sena.colombiando.colombiando_backend.entities.AddressEntity;
import com.sena.colombiando.colombiando_backend.mappers.AddressMapper;
import com.sena.colombiando.colombiando_backend.repositories.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(
            AddressRepository addressRepository,
            AddressMapper addressMapper
    ) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Transactional
    public AddressDto.Response createAddress(AddressDto.Create request){
        AddressEntity address = addressMapper.toEntity(request);
        addressRepository.save(address);
        return addressMapper.toDto(address);
    }

    @Transactional
    public AddressDto.Response updateAddress(UUID id, AddressDto.Update request) {
        var dataRequest = request.data();

        AddressEntity addressToUpdate = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada"));

        Optional.ofNullable(dataRequest.addressText())
                .ifPresent(addressToUpdate::setAddressText);
        Optional.ofNullable(dataRequest.countryCode())
                .ifPresent(addressToUpdate::setCountryCode);
        Optional.ofNullable(dataRequest.municipality())
                .ifPresent(addressToUpdate::setMunicipality);
        Optional.ofNullable(dataRequest.latitude())
                .ifPresent(addressToUpdate::setLatitude);
        Optional.ofNullable(dataRequest.longitude())
                .ifPresent(addressToUpdate::setLongitude);

        AddressEntity updated = addressRepository.save(addressToUpdate);
        return addressMapper.toDto(updated);
    }

    @Transactional
    public AddressDto.Response deleteAddress(UUID id){
        AddressEntity address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada"));
        addressRepository.delete(address);
        return addressMapper.toDto(address);
    }

    @Transactional
    public AddressDto.Response getAddress(UUID id){
        AddressEntity address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada"));
        return addressMapper.toDto(address);
    }

    @Transactional
    public List<AddressDto.Response> getAllAddresses(){
        List<AddressEntity> addressEntities = addressRepository.findAll();
        List<AddressDto.Response> responses = new ArrayList<>();

        for(AddressEntity addressEntity : addressEntities){
            responses.add(addressMapper.toDto(addressEntity));
        }

        return responses;
    }

    @Transactional
    public List<AddressDto.Response> getAllAddressesByCountryCode(String countryCode){
        List<AddressEntity> addressEntities = addressRepository.findByCountryCode(countryCode);
        List<AddressDto.Response> responses = new ArrayList<>();

        for(AddressEntity addressEntity : addressEntities){
            responses.add(addressMapper.toDto(addressEntity));
        }

        return responses;
    }

    @Transactional
    public List<AddressDto.Response> getAllAddressesByMunicipality(String municipality){
        List<AddressEntity> addressEntities = addressRepository.findByMunicipality(municipality);
        List<AddressDto.Response> responses = new ArrayList<>();

        for(AddressEntity addressEntity : addressEntities){
            responses.add(addressMapper.toDto(addressEntity));
        }

        return responses;
    }

}
