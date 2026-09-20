package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.PlaceDto;
import com.sena.colombiando.colombiando_backend.entities.PlaceEntity;
import com.sena.colombiando.colombiando_backend.entities.AddressEntity;
import com.sena.colombiando.colombiando_backend.repositories.AddressRepository;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public PlaceMapper(AddressRepository addressRepository,
                       AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public PlaceEntity toEntity(PlaceDto.Create request) {
        var entity = new PlaceEntity();
        var baseData = request.data();

        AddressEntity address = addressRepository.getReferenceById(request.addressID());

        entity.setName(baseData.name());
        entity.setDescription(baseData.description());
        entity.setHeaderImg(baseData.headerImg());
        entity.setAddress(address);

        return entity;
    }

    public PlaceDto.PlacePublic toPublicDto(PlaceEntity entity) {
        if (entity == null) {
            return null;
        }

        var baseData = new PlaceDto.Base(
                entity.getName(),
                entity.getDescription(),
                entity.getHeaderImg()
        );

        return new PlaceDto.PlacePublic(
                entity.getId(),
                baseData,
                addressMapper.toDto(entity.getAddress())
        );
    }

    public PlaceDto.Response toDto(PlaceEntity entity) {
        if (entity == null) {
            return null;
        }

        var baseData = new PlaceDto.Base(
                entity.getName(),
                entity.getDescription(),
                entity.getHeaderImg()
        );

        return new PlaceDto.Response(
                entity.getId(),
                baseData,
                addressMapper.toDto(entity.getAddress()),
                entity.getCreatedAt()
        );
    }

}
