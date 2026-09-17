package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.PlacesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository<PlacesEntity, UUID> {
    public List<PlacesEntity> findByAddressId(UUID addressId);
    public List<PlacesEntity> findByNameContainingIgnoreCase(String name);
}
