package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.PlaceDto;
import com.sena.colombiando.colombiando_backend.entities.AddressEntity;
import com.sena.colombiando.colombiando_backend.entities.PlaceEntity;
import com.sena.colombiando.colombiando_backend.mappers.PlaceMapper;
import com.sena.colombiando.colombiando_backend.repositories.AddressRepository;
import com.sena.colombiando.colombiando_backend.repositories.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlaceService {

    private final PlaceMapper placeMapper;
    private final PlaceRepository placeRepository;
    private final AddressRepository addressRepository;

    public PlaceService(PlaceMapper placeMapper, PlaceRepository placeRepository, AddressRepository addressRepository) {
        this.placeMapper = placeMapper;
        this.placeRepository = placeRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public PlaceDto.Response createPlace(PlaceDto.Create request) {
        PlaceEntity placeEntity = placeMapper.toEntity(request);

        AddressEntity address = addressRepository.getReferenceById(request.addressID());
        placeEntity.setAddress(address);

        placeRepository.save(placeEntity);
        return placeMapper.toDto(placeEntity);
    }

    @Transactional
    public PlaceDto.Response updatePlace(UUID id, PlaceDto.Update request) {
        var dataBase = request.data();
        PlaceEntity place = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lugar no encontrado."));

        Optional.ofNullable(dataBase.name()).ifPresent(place::setName);
        Optional.ofNullable(dataBase.description()).ifPresent(place::setDescription);
        Optional.ofNullable(dataBase.headerImg()).ifPresent(place::setHeaderImg);

        placeRepository.save(place);
        return placeMapper.toDto(place);
    }

    @Transactional
    public PlaceDto.Response deletePlace(UUID id) {
        PlaceEntity place = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lugar no encontrado."));
        placeRepository.delete(place);
        return placeMapper.toDto(place);
    }

    @Transactional
    public PlaceDto.Response getPlace(UUID id) {
        PlaceEntity place = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lugar no encontrado."));
        return placeMapper.toDto(place);
    }

    @Transactional
    public List<PlaceDto.Response> getPlaces() {
        List<PlaceEntity> places = placeRepository.findAll();
        List<PlaceDto.Response> response = new ArrayList<>();

        for (PlaceEntity place : places) {
            response.add(placeMapper.toDto(place));
        }

        return response;
    }

    @Transactional
    public PlaceDto.Response getPlaceByAddressId(UUID addressId) {
        PlaceEntity place = placeRepository.findByAddressId(addressId);

        if (place == null) {
            throw new EntityNotFoundException("Lugar no encontrado.");
        }

        return placeMapper.toDto(place);
    }

    @Transactional
    public List<PlaceDto.Response> getPlaceByNameContaining(String name) {
        List<PlaceEntity> place = placeRepository.findByNameContainingIgnoreCase(name);
        List<PlaceDto.Response> responses = new ArrayList<>();

        if (place.isEmpty()) {
            throw new EntityNotFoundException("Lugar no encontrado.");
        }

        for (PlaceEntity placeEntity : place) {
            responses.add(placeMapper.toDto(placeEntity));
        }

        return responses;
    }

}
