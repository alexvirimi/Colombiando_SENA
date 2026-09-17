package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.PaymentEntity;
import com.sena.colombiando.colombiando_backend.entities.PaymentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    public List<PaymentEntity> findByBookingId(UUID id);
    public List<PaymentEntity> findByBookingIdAndStatus(UUID id, PaymentStatusEnum status);
    public List<PaymentEntity> findByPaymentStatus(PaymentStatusEnum status);

    @Query("SELECT COALESCE(SUM(p.paidAmount), 0.0) FROM PaymentEntity p " +
            "WHERE p.booking.id = :bookingId AND p.status = :status")
    Double sumPaidAmountByIdBookingIdAndStatus(
            @Param("bookingId") UUID bookingId,
            @Param("status") PaymentStatusEnum status
    );
}
