package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.ScheduleInstanceDto;
import com.sena.colombiando.colombiando_backend.entities.ScheduleEntity;
import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceEntity;
import com.sena.colombiando.colombiando_backend.repositories.ScheduleRepository;
import org.springframework.stereotype.Component;

@Component
public class ScheduleInstanceMapper {

    private final ScheduleMapper scheduleMapper;
    private final ScheduleRepository scheduleRepository;

    public ScheduleInstanceMapper(
            ScheduleMapper scheduleMapper,
            ScheduleRepository scheduleRepository) {
        this.scheduleMapper = scheduleMapper;
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleInstanceEntity toEntity(ScheduleInstanceDto.Create request) {
        var entity = new ScheduleInstanceEntity();
        var dataBase = request.data();

        ScheduleEntity schedule = scheduleRepository.getReferenceById(request.scheduleId());

        entity.setSchedule(schedule);
        entity.setDate(dataBase.date());
        entity.setMaxCapacity(dataBase.maxCapacity());
        entity.setAvailableCapacity(dataBase.maxCapacity());

        return entity;
    }

    public ScheduleInstanceDto.Response toDto(ScheduleInstanceEntity entity) {
        if (entity == null) {
            return null;
        }

        var dataBase = new ScheduleInstanceDto.Base(
                entity.getDate(),
                entity.getMaxCapacity()
        );

        return new ScheduleInstanceDto.Response(
                entity.getId(),
                scheduleMapper.toDto(entity.getSchedule()),
                dataBase,
                entity.getAvailableCapacity(),
                entity.getState()
        );
    }

}
