package com.sena.colombiando.colombiando_backend.mappers;

import com.sena.colombiando.colombiando_backend.dto.PaymentDto;
import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.PaymentEntity;
import com.sena.colombiando.colombiando_backend.repositories.BookingRepository;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;

    public PaymentMapper(BookingMapper bookingMapper, BookingRepository bookingRepository) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
    }

    public PaymentEntity toEntity(PaymentDto.Create request) {
        var entity = new PaymentEntity();

        BookingEntity booking = bookingRepository.getReferenceById(request.bookingId());

        entity.setBooking(booking);
        entity.setAmountToPay(request.amountToPay());
        entity.setCurrency(request.currency());
        entity.setPaymentMethod(request.paymentMethod());

        return entity;
    }

    public PaymentDto.Response toDto(PaymentEntity entity) {
        if (entity == null) {
            return null;
        }

        return new PaymentDto.Response(
                entity.getId(),
                bookingMapper.toPublicDto(entity.getBooking()),
                entity.getAmountToPay(),
                entity.getPaymentMethod(),
                entity.getCurrency(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
