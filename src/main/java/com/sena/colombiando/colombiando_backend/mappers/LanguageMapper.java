package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.LanguageDto;
import com.sena.colombiando.colombiando_backend.entities.LanguageEntity;
import org.springframework.stereotype.Component;

@Component
public class LanguageMapper {

    public LanguageEntity toEntity(LanguageDto.Create request) {
        LanguageEntity entity = new LanguageEntity();

        entity.setCode(request.code());
        entity.setName(request.name());
        entity.setNativeName(request.nativeName());

        return entity;
    }

    public LanguageDto.Response toDto(LanguageEntity entity) {
        var dataBase = new LanguageDto.Create(
                entity.getCode(),
                entity.getName(),
                entity.getNativeName()
        );

        return new LanguageDto.Response(
                entity.getId(),
                dataBase
        );
    }
}
