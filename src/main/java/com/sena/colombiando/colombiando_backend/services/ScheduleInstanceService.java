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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScheduleInstanceService {

    private final ScheduleInstanceMapper scheduleInstanceMapper;
    private final ScheduleInstanceRepository scheduleInstanceRepository;
    private final ScheduleRepository scheduleRepository;

/** Inicializa la instancia.
 * @param scheduleInstanceMapper parametro de entrada.
 * @param scheduleInstanceRepository parametro de entrada.
 * @param scheduleRepository parametro de entrada.
 */
    public ScheduleInstanceService(
            ScheduleInstanceMapper scheduleInstanceMapper,
            ScheduleInstanceRepository scheduleInstanceRepository,
            ScheduleRepository scheduleRepository
    ) {
        this.scheduleInstanceMapper = scheduleInstanceMapper;
        this.scheduleInstanceRepository = scheduleInstanceRepository;
        this.scheduleRepository = scheduleRepository;
    }

/** Crea schedule instace.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public ScheduleInstanceDto.Response createScheduleInstace(ScheduleInstanceDto.Create request) {
        var dataBase = request.data();
        ScheduleEntity schedule = scheduleRepository.getReferenceById(request.scheduleId());

        ScheduleInstanceEntity scheduleInstance = scheduleInstanceMapper.toEntity(request);
        scheduleInstance.setSchedule(schedule);

        scheduleInstanceRepository.save(scheduleInstance);
        return scheduleInstanceMapper.toDto(scheduleInstance);
    }

/** Actualiza schedule instance.
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public ScheduleInstanceDto.Response updateScheduleInstance(UUID id, ScheduleInstanceDto.Update request) {
        ScheduleInstanceEntity scheduleInstance = scheduleInstanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instancia de horario no encontrada."));

        if (request.maxCapacity() != null) {
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

/** Elimina schedule instance.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public ScheduleInstanceDto.Response deleteScheduleInstance(UUID id) {
        ScheduleInstanceEntity scheduleInstance = scheduleInstanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instancia de horario no encontrada."));

        scheduleInstance.setState(ScheduleInstanceStateEnum.CANCELLED);
        scheduleInstance.setSchedule(null);
        scheduleInstanceRepository.save(scheduleInstance);
        return scheduleInstanceMapper.toDto(scheduleInstance);
    }

/** Consulta schedule instance.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public ScheduleInstanceDto.Response getScheduleInstance(UUID id) {
        ScheduleInstanceEntity scheduleInstance = scheduleInstanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instancia de horario no encontrada."));
        return scheduleInstanceMapper.toDto(scheduleInstance);
    }

/** Consulta search schedule instances.
 * @param scheduleID parametro de entrada.
 * @param placeId parametro de entrada.
 * @param state parametro de entrada.
 * @param fromDate parametro de entrada.
 * @param toDate parametro de entrada.
 * @param minCapacity parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public List<ScheduleInstanceDto.Response> searchScheduleInstances(
            UUID scheduleID, UUID placeId, ScheduleInstanceStateEnum state,
            LocalDate fromDate, LocalDate toDate, Integer minCapacity
    ) {
        List<ScheduleInstanceEntity> scheduleInstances = scheduleInstanceRepository.search(
                scheduleID, placeId, state, fromDate, toDate, minCapacity
        );
        if (scheduleInstances.isEmpty()) {
            return new ArrayList<>();
        }

        List<ScheduleInstanceDto.Response> responses = new ArrayList<>();

        for (ScheduleInstanceEntity scheduleInstance : scheduleInstances) {
            responses.add(scheduleInstanceMapper.toDto(scheduleInstance));
        }

        return responses;
    }

/** Reserva slots.
 * @param id parametro de entrada.
 * @param slots parametro de entrada.
 */
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

/** Libera slots.
 * @param id parametro de entrada.
 * @param slots parametro de entrada.
 */
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
