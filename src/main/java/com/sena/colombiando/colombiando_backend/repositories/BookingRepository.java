package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.BookingStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
    @Query("""
    SELECT b FROM BookingEntity b
    WHERE (:userId IS NULL OR b.user.id = :userId)
      AND (:scheduleInstanceId IS NULL OR b.scheduleInstance.id = :scheduleInstanceId)
      AND (:status IS NULL OR b.status = :status)
      AND (CAST(:fromDate AS date) IS NULL OR b.createdAt >= :fromDate)
      AND (CAST(:toDate AS date) IS NULL OR b.createdAt <= :toDate)
    """)
    List<BookingEntity> search(
            @Param("userId") UUID userId,
            @Param("scheduleInstanceId") UUID scheduleInstanceId,
            @Param("status") BookingStatusEnum status,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
