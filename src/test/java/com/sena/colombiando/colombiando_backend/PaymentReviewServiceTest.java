package com.sena.colombiando.colombiando_backend;

import com.sena.colombiando.colombiando_backend.dto.PaymentDto;
import com.sena.colombiando.colombiando_backend.dto.ReviewDto;
import com.sena.colombiando.colombiando_backend.entities.*;
import com.sena.colombiando.colombiando_backend.mappers.PaymentMapper;
import com.sena.colombiando.colombiando_backend.mappers.ReviewMapper;
import com.sena.colombiando.colombiando_backend.repositories.*;
import com.sena.colombiando.colombiando_backend.services.PaymentService;
import com.sena.colombiando.colombiando_backend.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentReviewServiceTest {

    @Mock PaymentRepository paymentRepository;
    @Mock PaymentMapper paymentMapper;
    @Mock BookingRepository bookingRepository;
    @Mock ReviewRepository reviewRepository;
    @Mock ReviewMapper reviewMapper;
    @Mock UserRepository userRepository;

    private PaymentService paymentService;
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(paymentRepository, paymentMapper, bookingRepository);
        reviewService = new ReviewService(reviewRepository, reviewMapper, userRepository, bookingRepository);
    }

    @Test
    void cpPag01_createsPaymentWithThreeCharacterCurrency() {
        UUID bookingId = UUID.randomUUID();
        PaymentDto.Create request = new PaymentDto.Create(
                bookingId, new BigDecimal("150000"), PaymentMethodEnum.CARD, "COP");
        BookingEntity booking = new BookingEntity();
        PaymentEntity payment = new PaymentEntity();
        when(bookingRepository.getReferenceById(bookingId)).thenReturn(booking);
        when(paymentMapper.toEntity(request)).thenReturn(payment);
        when(paymentMapper.toDto(payment)).thenReturn(null);

        paymentService.createPayment(request);

        assertSame(booking, payment.getBooking());
        assertEquals(3, request.currency().length());
        verify(paymentRepository).save(payment);
    }

    @Test
    void cpPag02_queriesUpdatesAndDeletesPayment() {
        UUID id = UUID.randomUUID();
        PaymentEntity payment = new PaymentEntity();
        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));
        when(paymentMapper.toDto(payment)).thenReturn(null);

        assertNull(paymentService.getPayment(id));
        paymentService.updatePayment(id, new PaymentDto.Update(
                new BigDecimal("160000"), "COP", PaymentMethodEnum.TRANSFER, PaymentStatusEnum.COMPLETED));
        paymentService.deletePayment(id);

        assertEquals(PaymentStatusEnum.CANCELLED, payment.getStatus());
        verify(paymentRepository, times(2)).save(payment);
    }

    @Test
    void cpRev01_createsReviewWithRatingFour() {
        UUID userId = UUID.randomUUID();
        UUID bookingId = UUID.randomUUID();
        ReviewDto.Create request = new ReviewDto.Create(userId, bookingId, 4, "Muy bueno");
        ReviewEntity review = new ReviewEntity();
        review.setRating(4);
        when(userRepository.getReferenceById(userId)).thenReturn(new UserEntity());
        when(bookingRepository.getReferenceById(bookingId)).thenReturn(new BookingEntity());
        when(reviewMapper.toEntity(request)).thenReturn(review);
        when(reviewMapper.toDto(review)).thenReturn(null);

        reviewService.createReview(request);

        assertEquals(4, review.getRating());
        verify(reviewRepository).save(review);
    }

    @Test
    void cpRev02_queriesUpdatesAndDeletesReview() {
        UUID id = UUID.randomUUID();
        ReviewEntity review = new ReviewEntity();
        review.setRating(3);
        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));
        when(reviewMapper.toDto(review)).thenReturn(null);

        assertNull(reviewService.getReviewById(id));
        reviewService.updateReview(id, new ReviewDto.Update(new ReviewDto.Base(5, "Excelente")));
        reviewService.deleteReview(id);

        assertEquals(5, review.getRating());
        verify(reviewRepository).delete(review);
        verify(reviewRepository).save(review);
    }
}
