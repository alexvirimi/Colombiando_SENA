package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.PaymentEntity;
import com.sena.colombiando.colombiando_backend.entities.PaymentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    public List<PaymentEntity> findByBookingId(UUID id);
    public List<PaymentEntity> findByBookingIdAndStatus(UUID id, PaymentStatusEnum status);
    public List<PaymentEntity> findByStatus(PaymentStatusEnum status);
}
