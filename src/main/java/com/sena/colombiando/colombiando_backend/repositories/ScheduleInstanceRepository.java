package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceEntity;
import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduleInstanceRepository extends JpaRepository<ScheduleInstanceEntity, UUID> {
    @Query("""
    SELECT si FROM ScheduleInstanceEntity si
    WHERE (:scheduleId IS NULL OR si.schedule.id = :scheduleId)
      AND (:placeId IS NULL OR si.schedule.place.id = :placeId)
      AND (:state IS NULL OR si.state = :state)
      AND (CAST(:date1 AS date) IS NULL OR si.date >= :date1)
      AND (CAST(:date2 AS date) IS NULL OR si.date <= :date2)
      AND (:minCapacity IS NULL OR si.availableCapacity > :minCapacity)
    """)
    List<ScheduleInstanceEntity> search(
            @Param("scheduleId") UUID scheduleId,
            @Param("placeId") UUID placeId,
            @Param("state") ScheduleInstanceStateEnum state,
            @Param("date1") LocalDate date1,
            @Param("date2") LocalDate date2,
            @Param("minCapacity") Integer minCapacity
    );

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
