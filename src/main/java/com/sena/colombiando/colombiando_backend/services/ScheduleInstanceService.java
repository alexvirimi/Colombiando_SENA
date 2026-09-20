package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.ScheduleInstanceDto;
import com.sena.colombiando.colombiando_backend.entities.ScheduleEntity;
import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceEntity;
import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceStateEnum;
import com.sena.colombiando.colombiando_backend.mappers.ScheduleInstanceMapper;
import com.sena.colombiando.colombiando_backend.repositories.ScheduleInstanceRepository;
import com.sena.colombiando.colombiando_backend.repositories.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ScheduleInstanceService {

    private final ScheduleInstanceMapper scheduleInstanceMapper;
    private final ScheduleInstanceRepository scheduleInstanceRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleInstanceService(
            ScheduleInstanceMapper scheduleInstanceMapper,
            ScheduleInstanceRepository scheduleInstanceRepository,
            ScheduleRepository scheduleRepository
    ) {
        this.scheduleInstanceMapper = scheduleInstanceMapper;
        this.scheduleInstanceRepository = scheduleInstanceRepository;
        this.scheduleRepository = scheduleRepository;
    }

    private List<ScheduleInstanceDto.Response> responses(List<ScheduleInstanceEntity> scheduleInstances) {
        if (scheduleInstances.isEmpty()) {
            return new ArrayList<>();
        }

        List<ScheduleInstanceDto.Response> responses = new ArrayList<>();

        for (ScheduleInstanceEntity scheduleInstance : scheduleInstances) {
            responses.add(scheduleInstanceMapper.toDto(scheduleInstance));
        }

        return responses;
    }

    @Transactional
    public ScheduleInstanceDto.Response createScheduleInstace(ScheduleInstanceDto.Create request) {
        var dataBase = request.data();
        ScheduleEntity schedule = scheduleRepository.getReferenceById(request.scheduleId());

        ScheduleInstanceEntity scheduleInstance = scheduleInstanceMapper.toEntity(request);
        scheduleInstance.setSchedule(schedule);

        scheduleInstanceRepository.save(scheduleInstance);
        return scheduleInstanceMapper.toDto(scheduleInstance);
    }

    @Transactional
    public ScheduleInstanceDto.Response updateScheduleInstance(UUID id, ScheduleInstanceDto.Update request) {
        ScheduleInstanceEntity scheduleInstance = scheduleInstanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instancia de horario no encontrada."));

        if (request.maxCapacity() > 0) {
            int currentMaxCapacity = scheduleInstance.getMaxCapacity();
            int currentAvailableCapacity = scheduleInstance.getAvailableCapacity();

            int currentlyBooked = currentMaxCapacity - currentAvailableCapacity;
            int newAvailableCapacity = request.maxCapacity() - currentlyBooked;

            if (newAvailableCapacity < 0) {
                throw new IllegalArgumentException("La nueva capacidad máxima es menor que las reservas existentes.");
            }

            scheduleInstance.setMaxCapacity(request.maxCapacity());
            scheduleInstance.setAvailableCapacity(newAvailableCapacity);
        }
        else {
            throw new IllegalArgumentException("La capacidad máxima debe ser mayor a cero.");
        }

        Optional.ofNullable(request.state()).ifPresent(scheduleInstance::setState);

        scheduleInstanceRepository.save(scheduleInstance);
        return scheduleInstanceMapper.toDto(scheduleInstance);
    }

    @Transactional
    public ScheduleInstanceDto.Response deleteScheduleInstance(UUID id) {
        ScheduleInstanceEntity scheduleInstance = scheduleInstanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instancia de horario no encontrada."));

        scheduleInstance.setState(ScheduleInstanceStateEnum.CANCELLED);
        scheduleInstanceRepository.save(scheduleInstance);
        return scheduleInstanceMapper.toDto(scheduleInstance);
    }

    @Transactional
    public ScheduleInstanceDto.Response getScheduleInstance(UUID id) {
        ScheduleInstanceEntity scheduleInstance = scheduleInstanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instancia de horario no encontrada."));
        return scheduleInstanceMapper.toDto(scheduleInstance);
    }

    @Transactional
    public List<ScheduleInstanceDto.Response> getScheduleInstances() {
        List<ScheduleInstanceEntity> scheduleInstances = scheduleInstanceRepository.findAll();
        return responses(scheduleInstances);
    }

    @Transactional
    public List<ScheduleInstanceDto.Response> getByScheduleId(UUID scheduleId) {
        List<ScheduleInstanceEntity> scheduleInstances = scheduleInstanceRepository.findByScheduleId(scheduleId);
        return responses(scheduleInstances);
    }

    @Transactional
    public List<ScheduleInstanceDto.Response> getByScheduleIdAndDate(UUID scheduleId, LocalDate date) {
        List<ScheduleInstanceEntity> scheduleInstances = scheduleInstanceRepository.findByScheduleIdAndDate(scheduleId, date);
        return responses(scheduleInstances);
    }

    @Transactional
    public List<ScheduleInstanceDto.Response> getByDate(LocalDate date) {
        List<ScheduleInstanceEntity> scheduleInstances = scheduleInstanceRepository.findByDate(date);
        return responses(scheduleInstances);
    }

    @Transactional
    public List<ScheduleInstanceDto.Response> getByDateBetween(LocalDate date1, LocalDate date2) {
        List<ScheduleInstanceEntity> scheduleInstances = scheduleInstanceRepository.findByDateBetween(date1, date2);
        return responses(scheduleInstances);
    }

    @Transactional
    public List<ScheduleInstanceDto.Response> getByStateAndAvailableCapacityGreaterThan(ScheduleInstanceStateEnum state, int capacity) {
        List<ScheduleInstanceEntity> scheduleInstances = scheduleInstanceRepository.findByStateAndAvailableCapacityGreaterThan(state, capacity);
        return responses(scheduleInstances);
    }

    @Transactional
    public List<ScheduleInstanceDto.Response> getBySchedule_PlaceIdAndDateBetweenAndState(
            UUID placeId, LocalDate date1, LocalDate date2, ScheduleInstanceStateEnum state
    ) {
        List<ScheduleInstanceEntity> scheduleInstances = scheduleInstanceRepository.findBySchedule_PlaceIdAndDateBetweenAndState(
                placeId, date1, date2, state
        );
        return responses(scheduleInstances);
    }

    @Transactional
    public void bookSlots(UUID id, int slots) {
        if (slots <= 0) {
            throw new IllegalArgumentException("Los cupos a reservar deben ser mayor a cero.");
        }

        int updatedRows = scheduleInstanceRepository.decreaseCapacity(id, slots);

        if (updatedRows == 0) {
            throw new IllegalStateException("No fue posible realizar la reserva. Cupos insuficientes o el horario no existe.");
        }
    }

    @Transactional
    public void releaseSlots(UUID id, int slots) {
        if (slots <= 0) {
            throw new IllegalArgumentException("Los cupos a liberar deben ser mayor a cero.");
        }

        int updatedRows = scheduleInstanceRepository.restoreCapacity(id, slots);

        if (updatedRows == 0) {
            throw new IllegalArgumentException("No se pudo restaurar la capacidad. El horario especificado no existe.");
        }
    }


}
