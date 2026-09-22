package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.AddressDto;
import com.sena.colombiando.colombiando_backend.entities.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

/** Convierte los datos mediante to entity.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    public AddressEntity toEntity(AddressDto.Create request) {
        var entity = new AddressEntity();
        var baseData = request.data();

        entity.setAddressText(baseData.addressText());
        entity.setCountryCode(baseData.countryCode());
        entity.setMunicipality(baseData.municipality());
        entity.setLatitude(baseData.latitude());
        entity.setLongitude(baseData.longitude());

        return entity;
    }

/** Convierte los datos mediante to dto.
 * @param entity parametro de entrada.
 * @return resultado de la operacion.
 */
    public AddressDto.Response toDto(AddressEntity entity) {
        if(entity == null) {
            return null;
        }

        var baseData = new AddressDto.Base(
                entity.getAddressText(),
                entity.getCountryCode(),
                entity.getMunicipality(),
                entity.getLatitude(),
                entity.getLongitude()
        );

        return new AddressDto.Response(
                entity.getId(),
                baseData
        );
    }

}
