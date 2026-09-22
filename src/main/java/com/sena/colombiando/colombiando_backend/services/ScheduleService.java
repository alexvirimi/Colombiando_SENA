package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.ScheduleDto;
import com.sena.colombiando.colombiando_backend.entities.GuideEntity;
import com.sena.colombiando.colombiando_backend.entities.PlaceEntity;
import com.sena.colombiando.colombiando_backend.entities.ScheduleEntity;
import com.sena.colombiando.colombiando_backend.entities.ScheduleStatusEnum;
import com.sena.colombiando.colombiando_backend.mappers.ScheduleMapper;
import com.sena.colombiando.colombiando_backend.repositories.GuideRepository;
import com.sena.colombiando.colombiando_backend.repositories.PlaceRepository;
import com.sena.colombiando.colombiando_backend.repositories.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final GuideRepository guideRepository;
    private final PlaceRepository placeRepository;

/** Inicializa la instancia.
 * @param scheduleRepository parametro de entrada.
 * @param scheduleMapper parametro de entrada.
 * @param guideRepository parametro de entrada.
 * @param placeRepository parametro de entrada.
 */
    public ScheduleService(
            ScheduleRepository scheduleRepository,
            ScheduleMapper scheduleMapper,
            GuideRepository guideRepository,
            PlaceRepository placeRepository
    ) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.guideRepository = guideRepository;
        this.placeRepository = placeRepository;
    }

/** Crea schedule.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public ScheduleDto.Response createSchedule(ScheduleDto.Create request) {
        GuideEntity guide = guideRepository.getReferenceById(request.guideId());
        PlaceEntity place = placeRepository.getReferenceById(request.placeId());

        ScheduleEntity scheduleEntity = scheduleMapper.toEntity(request);
        scheduleEntity.setGuide(guide);
        scheduleEntity.setPlace(place);

        scheduleRepository.save(scheduleEntity);
        return scheduleMapper.toDto(scheduleEntity);
    }

/** Actualiza schedule.
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public ScheduleDto.Response updateSchedule(UUID id, ScheduleDto.Update request) {
        var dataBase = request.data();

        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horario no encontrado."));

        Optional.ofNullable(request.status()).ifPresent(schedule::setStatus);
        Optional.ofNullable(request.maxCapacity()).ifPresent(schedule::setMaxCapacity);
        Optional.ofNullable(request.pricePerPerson()).ifPresent(schedule::setPricePerPerson);
        Optional.ofNullable(dataBase.startTime()).ifPresent(schedule::setStartTime);
        Optional.ofNullable(dataBase.endTime()).ifPresent(schedule::setEndTime);
        Optional.ofNullable(dataBase.startDate()).ifPresent(schedule::setStartDate);
        Optional.ofNullable(dataBase.endDate()).ifPresent(schedule::setEndDate);

        scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

/** Elimina schedule.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public ScheduleDto.Response deleteSchedule(UUID id) {
        var schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horario no encontrado."));

        schedule.setStatus(ScheduleStatusEnum.INACTIVE);

        scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

/** Consulta schedule.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public ScheduleDto.Response getSchedule(UUID id) {
        var schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horario no encontrado."));
        return scheduleMapper.toDto(schedule);
    }

/** Consulta search schedules.
 * @param guideId parametro de entrada.
 * @param placeId parametro de entrada.
 * @param status parametro de entrada.
 * @param startDate parametro de entrada.
 * @param endDate parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public List<ScheduleDto.Response> searchSchedules(
            UUID guideId, UUID placeId, ScheduleStatusEnum status,
            LocalDate startDate, LocalDate endDate
    ) {
        List<ScheduleEntity> schedules = scheduleRepository.search(guideId, placeId, status, startDate, endDate);
        List<ScheduleDto.Response> responses = new ArrayList<>();
        for (ScheduleEntity schedule : schedules) {
            responses.add(scheduleMapper.toDto(schedule));
        }
        return responses;
    }

}
