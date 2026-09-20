package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.BookingEscortEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingEscortsRepository extends JpaRepository<BookingEscortEntity, UUID> {
    public List<BookingEscortEntity> findByBookingId(UUID bookingId);
    public int countByBookingId(UUID bookingId);

    @Transactional
    void deleteByBookingId(UUID bookingId);
}
