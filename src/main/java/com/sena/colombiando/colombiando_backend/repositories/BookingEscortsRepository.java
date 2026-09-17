package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.BookingEscortsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingEscortsRepository extends JpaRepository<BookingEscortsEntity, UUID> {
    public List<BookingEscortsEntity> findByBookingId(UUID bookingId);
    public Integer countByBookingId(UUID bookingId);

    @Transactional
    void deleteByBookingId(UUID bookingId);
}
