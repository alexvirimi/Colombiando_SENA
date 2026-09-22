package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.ScheduleDto;
import com.sena.colombiando.colombiando_backend.entities.GuideEntity;
import com.sena.colombiando.colombiando_backend.entities.PlaceEntity;
import com.sena.colombiando.colombiando_backend.entities.ScheduleEntity;
import com.sena.colombiando.colombiando_backend.repositories.GuideRepository;
import com.sena.colombiando.colombiando_backend.repositories.PlaceRepository;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    private final GuideMapper guideMapper;
    private final PlaceMapper placeMapper;

    private final GuideRepository guideRepository;
    private final PlaceRepository placeRepository;

    public ScheduleMapper(
            GuideMapper guideMapper,
            PlaceMapper placeMapper,
            GuideRepository guideRepository,
            PlaceRepository placeRepository
    ) {
        this.guideMapper = guideMapper;
        this.placeMapper = placeMapper;
        this.guideRepository = guideRepository;
        this.placeRepository = placeRepository;
    }

    public ScheduleEntity toEntity(ScheduleDto.Create request) {
        var entity = new ScheduleEntity();
        var baseData = request.data();

        GuideEntity guide = guideRepository.getReferenceById(request.guideId());
        PlaceEntity place = placeRepository.getReferenceById(request.placeId());

        entity.setStartTime(baseData.startTime());
        entity.setEndTime(baseData.endTime());
        entity.setStartDate(baseData.startDate());
        entity.setEndDate(baseData.endDate());
        entity.setMaxCapacity(request.maxCapacity());
        entity.setPricePerPerson(request.pricePerPerson());
        entity.setGuide(guide);
        entity.setPlace(place);

        return entity;
    }

    public ScheduleDto.Response toDto(ScheduleEntity entity) {
        if (entity == null) {
            return null;
        }

        var baseData = new ScheduleDto.Base(
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getStartDate(),
                entity.getEndDate()
        );

        return new ScheduleDto.Response(
                entity.getId(),
                guideMapper.toPublicDto(entity.getGuide()),
                placeMapper.toPublicDto(entity.getPlace()),
                baseData,
                entity.getMaxCapacity(),
                entity.getPricePerPerson(),
                entity.getStatus()
        );
    }
}
