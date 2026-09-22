package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.PaymentEntity;
import com.sena.colombiando.colombiando_backend.entities.PaymentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    @Query("""
    SELECT p FROM PaymentEntity p
    WHERE (:bookingId IS NULL OR p.booking.id = :bookingId)
      AND (:status IS NULL OR p.status = :status)
    """)
    List<PaymentEntity> search(
            @Param("bookingId") UUID bookingId,
            @Param("status") PaymentStatusEnum status
    );
}
