package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.PlaceDto;
import com.sena.colombiando.colombiando_backend.entities.AddressEntity;
import com.sena.colombiando.colombiando_backend.entities.PlaceEntity;
import com.sena.colombiando.colombiando_backend.mappers.PlaceMapper;
import com.sena.colombiando.colombiando_backend.repositories.AddressRepository;
import com.sena.colombiando.colombiando_backend.repositories.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlaceService {

    private final PlaceMapper placeMapper;
    private final PlaceRepository placeRepository;
    private final AddressRepository addressRepository;

/** Inicializa la instancia.
 * @param placeMapper parametro de entrada.
 * @param placeRepository parametro de entrada.
 * @param addressRepository parametro de entrada.
 */
    public PlaceService(PlaceMapper placeMapper, PlaceRepository placeRepository, AddressRepository addressRepository) {
        this.placeMapper = placeMapper;
        this.placeRepository = placeRepository;
        this.addressRepository = addressRepository;
    }

/** Crea place.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public PlaceDto.Response createPlace(PlaceDto.Create request) {
        PlaceEntity placeEntity = placeMapper.toEntity(request);

        AddressEntity address = addressRepository.findById(request.addressID())
                .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada."));
        placeEntity.setAddress(address);

        placeRepository.save(placeEntity);
        return placeMapper.toDto(placeEntity);
    }

/** Actualiza place.
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
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

/** Elimina place.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public PlaceDto.Response deletePlace(UUID id) {
        PlaceEntity place = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lugar no encontrado."));
        placeRepository.delete(place);
        return placeMapper.toDto(place);
    }

/** Consulta place.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public PlaceDto.Response getPlace(UUID id) {
        PlaceEntity place = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lugar no encontrado."));
        return placeMapper.toDto(place);
    }

/** Consulta all places.
 * @return resultado de la operacion.
 */
    @Transactional
    public List<PlaceDto.Response> getAllPlaces() {
        List<PlaceEntity> places = placeRepository.findAll();
        List<PlaceDto.Response> response = new ArrayList<>();

        for (PlaceEntity place : places) {
            response.add(placeMapper.toDto(place));
        }

        return response;
    }

/** Consulta place by address id.
 * @param addressId parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public PlaceDto.Response getPlaceByAddressId(UUID addressId) {
        PlaceEntity place = placeRepository.findByAddressId(addressId);

        if (place == null) {
            throw new EntityNotFoundException("Lugar no encontrado.");
        }

        return placeMapper.toDto(place);
    }

/** Consulta place by name containing.
 * @param name parametro de entrada.
 * @return resultado de la operacion.
 */
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
