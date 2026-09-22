package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LanguageRepository extends JpaRepository<LanguageEntity, UUID> {
/** Consulta find by code.
 * @param code parametro de entrada.
 * @return resultado de la operacion.
 */
    public LanguageEntity findByCode(String code);
/** Ejecuta la operacion exists by code.
 * @param code parametro de entrada.
 * @return resultado de la operacion.
 */
    public boolean existsByCode(String code);
}
