package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
/** Consulta find by email.
 * @param email parametro de entrada.
 * @return resultado de la operacion.
 */
    public UserEntity findByEmail(String email);
/** Ejecuta la operacion exists by email.
 * @param email parametro de entrada.
 * @return resultado de la operacion.
 */
    public boolean existsByEmail(String email);
}
