package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.BookingStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
    public List<BookingEntity> findByUserId(UUID id);
    public List<BookingEntity> findByScheduleInstanceId(UUID id);
    public List<BookingEntity> findByStatus(BookingStatusEnum status);
    public List<BookingEntity> findByUserIdAndStatus(UUID id, BookingStatusEnum status);
    public List<BookingEntity> findByScheduleInstanceIdAndStatus(UUID id, BookingStatusEnum status);
    public List<BookingEntity> findByCreatedAtBetween(LocalDate from, LocalDate to);
}
