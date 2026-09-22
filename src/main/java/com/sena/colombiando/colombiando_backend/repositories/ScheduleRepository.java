package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.ScheduleEntity;
import com.sena.colombiando.colombiando_backend.entities.ScheduleStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {
    @Query("""
    SELECT s FROM ScheduleEntity s
    WHERE (:guideId IS NULL OR s.guide.id = :guideId)
      AND (:placeId IS NULL OR s.place.id = :placeId)
      AND (:status IS NULL OR s.status = :status)
      AND (:startDate IS NULL OR s.startDate <= :startDate)
      AND (:endDate IS NULL OR s.endDate >= :endDate)
    """)
    List<ScheduleEntity> search(
            @Param("guideId") UUID guideId,
            @Param("placeId") UUID placeId,
            @Param("status") ScheduleStatusEnum status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
