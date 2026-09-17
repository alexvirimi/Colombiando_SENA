package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.BookingStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
    public List<BookingEntity> findByUserId(UUID id);
    public List<BookingEntity> findByInstanceId(UUID id);
    public List<BookingEntity> findByStatus(BookingStatusEnum status);
    public List<BookingEntity> findByUserIdAndStatus(UUID id, BookingStatusEnum status);
    public List<BookingEntity> findByInstanceIdAndStatus(UUID id, BookingStatusEnum status);
    public List<BookingEntity> findByCreatedAtBetween(Date from, Date to);
}
