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

    private List<ScheduleDto.Response> responses(List<ScheduleEntity> schedules) {
        List<ScheduleDto.Response> responses = new ArrayList<>();

        for (ScheduleEntity schedule : schedules) {
            responses.add(scheduleMapper.toDto(schedule));
        }

        return responses;
    }

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

    @Transactional
    public ScheduleDto.Response updateSchedule(UUID id, ScheduleDto.Update request) {
        var dataBase = request.data();

        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horario no encontrado."));

        Optional.ofNullable(request.status()).ifPresent(schedule::setStatus);
        Optional.ofNullable(dataBase.startTime()).ifPresent(schedule::setStartTime);
        Optional.ofNullable(dataBase.endTime()).ifPresent(schedule::setEndTime);
        Optional.ofNullable(dataBase.startDate()).ifPresent(schedule::setStartDate);
        Optional.ofNullable(dataBase.endDate()).ifPresent(schedule::setEndDate);
        Optional.ofNullable(dataBase.maxCapacity()).ifPresent(schedule::setMaxCapacity);
        Optional.ofNullable(dataBase.pricePerPerson()).ifPresent(schedule::setPricePerPerson);

        scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

    @Transactional
    public ScheduleDto.Response deleteSchedule(UUID id) {
        var schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horario no encontrado."));

        schedule.setStatus(ScheduleStatusEnum.INACTIVE);

        scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

    @Transactional
    public ScheduleDto.Response getSchedule(UUID id) {
        var schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horario no encontrado."));
        return scheduleMapper.toDto(schedule);
    }

    @Transactional
    public List<ScheduleDto.Response> getAllSchedules() {
        List<ScheduleEntity> schedules = scheduleRepository.findAll();
        return responses(schedules);
    }

    @Transactional
    public List<ScheduleDto.Response> getAllByGuideId(UUID id) {
        List<ScheduleEntity> schedules = scheduleRepository.findByGuideId(id);
        return responses(schedules);
    }

    @Transactional
    public List<ScheduleDto.Response> getAllByPlaceId(UUID id) {
        List<ScheduleEntity> schedules = scheduleRepository.findByPlaceId(id);
        return responses(schedules);
    }

    @Transactional
    public List<ScheduleDto.Response> getAllByStatus(ScheduleStatusEnum status) {
        List<ScheduleEntity> schedules = scheduleRepository.findByStatus(status);
        return responses(schedules);
    }

    @Transactional
    public List<ScheduleDto.Response> getAllByGuideIdAndStatus(UUID guideId, ScheduleStatusEnum status, UUID id) {
        List<ScheduleEntity> schedules = scheduleRepository.findByGuideIdAndStatus(guideId, status);
        return responses(schedules);
    }

    @Transactional
    public List<ScheduleDto.Response> getAllByPlaceIdAndStartDate(UUID placeId, LocalDate startDate) {
        List<ScheduleEntity> schedules = scheduleRepository.findByPlaceIdAndStartDate(placeId, startDate);
        return responses(schedules);
    }

    @Transactional
    public List<ScheduleDto.Response> getAllByPlaceIdAndStartDateAndStatus(UUID placeId, LocalDate startDate, ScheduleStatusEnum status) {
        List<ScheduleEntity> schedules = scheduleRepository.findByPlaceIdAndStartDateAndStatus(placeId, startDate, status);
        return responses(schedules);
    }

    @Transactional
    public List<ScheduleDto.Response> getAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate) {
        List<ScheduleEntity> schedules = scheduleRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(startDate, endDate);
        return responses(schedules);
    }

}
