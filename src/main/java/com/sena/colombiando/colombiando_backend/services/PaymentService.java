package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.PaymentDto;
import com.sena.colombiando.colombiando_backend.entities.BookingEntity;
import com.sena.colombiando.colombiando_backend.entities.PaymentEntity;
import com.sena.colombiando.colombiando_backend.entities.PaymentStatusEnum;
import com.sena.colombiando.colombiando_backend.mappers.PaymentMapper;
import com.sena.colombiando.colombiando_backend.repositories.BookingRepository;
import com.sena.colombiando.colombiando_backend.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final BookingRepository bookingRepository;

/** Inicializa la instancia.
 * @param paymentRepository parametro de entrada.
 * @param paymentMapper parametro de entrada.
 * @param bookingRepository parametro de entrada.
 */
    public PaymentService(
            PaymentRepository paymentRepository,
            PaymentMapper paymentMapper,
            BookingRepository bookingRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.bookingRepository = bookingRepository;
    }

/** Crea payment.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public PaymentDto.Response createPayment(PaymentDto.Create request){
        BookingEntity booking = bookingRepository.getReferenceById(request.bookingId());

        PaymentEntity payment = paymentMapper.toEntity(request);
        payment.setBooking(booking);

        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

/** Actualiza payment.
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public PaymentDto.Response updatePayment(UUID id, PaymentDto.Update request){
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recibo de pago no encontrado."));

        Optional.ofNullable(request.status()).ifPresent(payment::setStatus);
        Optional.ofNullable(request.currency()).ifPresent(payment::setCurrency);
        Optional.ofNullable(request.amountToPay()).ifPresent(payment::setAmountToPay);
        Optional.ofNullable(request.paymentMethod()).ifPresent(payment::setPaymentMethod);

        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

/** Elimina payment.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public PaymentDto.Response deletePayment(UUID id){
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recibo de pago no encontrado."));

        payment.setStatus(PaymentStatusEnum.CANCELLED);
        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

/** Consulta payment.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public PaymentDto.Response getPayment(UUID id){
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recibo de pago no encontrado."));
        return paymentMapper.toDto(payment);
    }

/** Consulta search payments.
 * @param bookingId parametro de entrada.
 * @param status parametro de entrada.
 * @return resultado de la operacion.
 */
    @Transactional
    public List<PaymentDto.Response> searchPayments(
            UUID bookingId, PaymentStatusEnum status
    ){
        List<PaymentEntity> payments = paymentRepository.findAll();
        List<PaymentDto.Response> responses = new ArrayList<>();

        for (PaymentEntity payment : payments) {
            responses.add(paymentMapper.toDto(payment));
        }

        return responses;
    }

}
