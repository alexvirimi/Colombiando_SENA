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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final BookingRepository bookingRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            PaymentMapper paymentMapper,
            BookingRepository bookingRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.bookingRepository = bookingRepository;
    }

    private List<PaymentDto.Response> responses(List<PaymentEntity> payments) {
        List<PaymentDto.Response> responses = new ArrayList<>();

        for (PaymentEntity payment : payments) {
            responses.add(paymentMapper.toDto(payment));
        }

        return responses;
    }

    @Transactional
    public PaymentDto.Response createPayment(PaymentDto.Create request){
        BookingEntity booking = bookingRepository.getReferenceById(request.bookingId());

        PaymentEntity payment = paymentMapper.toEntity(request);
        payment.setBooking(booking);

        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

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

    @Transactional
    public PaymentDto.Response deletePayment(UUID id){
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recibo de pago no encontrado."));

        payment.setStatus(PaymentStatusEnum.CANCELLED);
        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Transactional
    public PaymentDto.Response getPayment(UUID id){
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recibo de pago no encontrado."));
        return paymentMapper.toDto(payment);
    }

    @Transactional
    public List<PaymentDto.Response> getPayments(){
        List<PaymentEntity> payments = paymentRepository.findAll();
        return responses(payments);
    }

    @Transactional
    public List<PaymentDto.Response> getPaymentsByBooking(UUID id){
        List<PaymentEntity> payments = paymentRepository.findByBookingId(id);
        return responses(payments);
    }

    @Transactional
    public List<PaymentDto.Response> getPaymentsByBookingAndStatus(
            UUID bookingId, PaymentStatusEnum status
    ){
        List<PaymentEntity> payments = paymentRepository.findByBookingIdAndStatus(bookingId, status);
        return responses(payments);
    }

    @Transactional
    public List<PaymentDto.Response> getPaymentByStatus(PaymentStatusEnum status){
        List<PaymentEntity> payments = paymentRepository.findByStatus(status);
        return responses(payments);
    }

}
