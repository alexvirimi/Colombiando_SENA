package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.GuideEntity;
import com.sena.colombiando.colombiando_backend.entities.GuideStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GuideRepository extends JpaRepository<GuideEntity, UUID> {
/** Consulta find by status.
 * @param status parametro de entrada.
 * @return resultado de la operacion.
 */
    public List<GuideEntity> findByStatus(GuideStatusEnum status);
/** Consulta find by name containing ignore case or last name containing ignore case.
 * @param name parametro de entrada.
 * @param lastName parametro de entrada.
 * @return resultado de la operacion.
 */
    public List<GuideEntity> findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String name, String lastName);
}
