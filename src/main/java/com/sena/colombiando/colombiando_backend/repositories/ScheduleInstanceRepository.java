package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceEntity;
import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ScheduleInstanceRepository extends JpaRepository<ScheduleInstanceEntity, UUID> {
    public List<ScheduleInstanceEntity> findByScheduleId(UUID scheduleId);
    public List<ScheduleInstanceEntity> findByScheduleIdAndDate(UUID scheduleId, Date date);
    public List<ScheduleInstanceEntity> findByDate(Date date);
    public List<ScheduleInstanceEntity> findByDateBetween(Date date1, Date date2);
    public List<ScheduleInstanceEntity> findByStateAndAvailableCapacityGreaterThan(ScheduleInstanceStateEnum state, int capacity);
    public List<ScheduleInstanceEntity> findBySchedule_PlaceIdAndDateBetweenAndState(UUID placeId, Date date1, Date date2, ScheduleInstanceStateEnum state);

    @Modifying
    @Query("UPDATE ScheduleInstanceEntity s " +
            "SET s.availableCapacity = s.availableCapacity - :n " +
            "WHERE s.id = :id AND s.availableCapacity >= :n")
    int decreaseCapacity(@Param("id") UUID id, @Param("n") int n);

    @Modifying
    @Query("UPDATE ScheduleInstanceEntity s " +
            "SET s.availableCapacity = s.availableCapacity + :n " +
            "WHERE s.id = :id")
    int restoreCapacity(@Param("id") UUID id, @Param("n") int n);
}
