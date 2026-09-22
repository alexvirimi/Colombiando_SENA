package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {
/** Consulta find by address id.
 * @param addressId parametro de entrada.
 * @return resultado de la operacion.
 */
    public PlaceEntity findByAddressId(UUID addressId);
/** Consulta find by name containing ignore case.
 * @param name parametro de entrada.
 * @return resultado de la operacion.
 */
    public List<PlaceEntity> findByNameContainingIgnoreCase(String name);
}
