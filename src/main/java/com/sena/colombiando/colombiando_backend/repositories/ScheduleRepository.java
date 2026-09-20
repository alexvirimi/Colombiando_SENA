package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.ScheduleEntity;
import com.sena.colombiando.colombiando_backend.entities.ScheduleStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {
    public List<ScheduleEntity> findByGuideId(UUID guideId);
    public List<ScheduleEntity> findByPlaceId(UUID placeId);
    public List<ScheduleEntity> findByStatus(ScheduleStatusEnum status);
    public List<ScheduleEntity> findByGuideIdAndStatus(UUID guideId, ScheduleStatusEnum status);
    public List<ScheduleEntity> findByPlaceIdAndStartDate(UUID placeId, LocalDate startDate);
    public List<ScheduleEntity> findByPlaceIdAndStartDateAndStatus(UUID placeId, LocalDate startDate, ScheduleStatusEnum status);
    public List<ScheduleEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);
}
