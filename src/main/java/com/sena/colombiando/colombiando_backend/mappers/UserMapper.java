package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.UserDto;
import com.sena.colombiando.colombiando_backend.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

/** Convierte los datos mediante to entity.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    public UserEntity toEntity(UserDto.Create request) {
        var entity = new UserEntity();
        var baseData = request.data();

        entity.setName(baseData.name());
        entity.setLastName(baseData.lastName());
        entity.setEmail(baseData.email());
        entity.setPhone(baseData.phone());
        entity.setPassword(request.password());

        return entity;
    }

/** Convierte los datos mediante to public dto.
 * @param entity parametro de entrada.
 * @return resultado de la operacion.
 */
    public UserDto.UserPublic toPublicDto(UserEntity entity) {
        if(entity == null) {
            return null;
        }

        return new UserDto.UserPublic(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getEmail()
        );
    }

/** Convierte los datos mediante to dto.
 * @param entity parametro de entrada.
 * @return resultado de la operacion.
 */
    public UserDto.Response toDto(UserEntity entity) {
        if(entity == null) {
            return null;
        }

        var baseData = new UserDto.Base(
                entity.getName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone()
        );

        return new UserDto.Response(
                entity.getId(),
                baseData,
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

}
