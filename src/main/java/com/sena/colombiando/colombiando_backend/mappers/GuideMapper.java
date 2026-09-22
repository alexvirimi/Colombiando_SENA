package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.GuideDto;
import com.sena.colombiando.colombiando_backend.entities.GuideEntity;
import org.springframework.stereotype.Component;

@Component
public class GuideMapper {

/** Convierte los datos mediante to entity.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    public GuideEntity toEntity(GuideDto.Create request) {
        var entity = new GuideEntity();
        var baseData = request.data();

        entity.setName(baseData.name());
        entity.setLastName(baseData.lastName());
        entity.setBio(baseData.bio());
        entity.setPhone(baseData.phone());
        entity.setProfileImage(baseData.profileImage());

        return entity;
    }

/** Convierte los datos mediante to public dto.
 * @param entity parametro de entrada.
 * @return resultado de la operacion.
 */
    public GuideDto.GuidePublic toPublicDto(GuideEntity entity) {
        if(entity == null) {
            return null;
        }

        return new GuideDto.GuidePublic(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getBio(),
                entity.getProfileImage(),
                entity.getStatus()
        );
    }

/** Convierte los datos mediante to dto.
 * @param entity parametro de entrada.
 * @return resultado de la operacion.
 */
    public GuideDto.Response toDto(GuideEntity entity) {
        if (entity == null) {
            return null;
        }

        var baseData = new GuideDto.Base(
                entity.getName(),
                entity.getLastName(),
                entity.getBio(),
                entity.getPhone(),
                entity.getProfileImage()
        );

        return new GuideDto.Response(
                entity.getId(),
                baseData,
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

}
