package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
/** Consulta find by country code.
 * @param countryCode parametro de entrada.
 * @return resultado de la operacion.
 */
    public List<AddressEntity> findByCountryCode(String countryCode);
/** Consulta find by municipality.
 * @param municipality parametro de entrada.
 * @return resultado de la operacion.
 */
    public List<AddressEntity> findByMunicipality(String municipality);
}
