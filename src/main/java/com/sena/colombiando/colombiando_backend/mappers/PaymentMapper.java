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

/** Inicializa la instancia.
 * @param bookingMapper parametro de entrada.
 * @param bookingRepository parametro de entrada.
 */
    public PaymentMapper(BookingMapper bookingMapper, BookingRepository bookingRepository) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
    }

/** Convierte los datos mediante to entity.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    public PaymentEntity toEntity(PaymentDto.Create request) {
        var entity = new PaymentEntity();

        BookingEntity booking = bookingRepository.getReferenceById(request.bookingId());

        entity.setBooking(booking);
        entity.setAmountToPay(request.amountToPay());
        entity.setCurrency(request.currency());
        entity.setPaymentMethod(request.paymentMethod());

        return entity;
    }

/** Convierte los datos mediante to dto.
 * @param entity parametro de entrada.
 * @return resultado de la operacion.
 */
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
