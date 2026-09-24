package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "payments",
        indexes = {
                @Index(
                        name = "idx_payment_booking",
                        columnList = "booking_id"
                )
        }
)
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "booking_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_payment_booking",
                    foreignKeyDefinition = "FOREIGN KEY (booking_id) " +
                            "REFERENCES bookings(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    private BookingEntity booking;

    @Column(name = "amount_to_pay", precision = 12, scale = 2, nullable = false)
    private BigDecimal amountToPay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethodEnum paymentMethod;

    @Column(length = 3, nullable = false)
    private String currency = "COP";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @org.hibernate.annotations.ColumnDefault("'PENDING'")
    private PaymentStatusEnum status = PaymentStatusEnum.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

/** Inicializa la instancia.
 */
    public PaymentEntity() {}

    // Getters & Setters

/** Consulta id.
 * @return resultado de la operacion.
 */
    public UUID getId() {
        return id;
    }

/** Consulta booking.
 * @return resultado de la operacion.
 */
    public BookingEntity getBooking() {
        return booking;
    }

/** Actualiza booking.
 * @param booking parametro de entrada.
 */
    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }

/** Consulta amount to pay.
 * @return resultado de la operacion.
 */
    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

/** Actualiza amount to pay.
 * @param amountToPay parametro de entrada.
 */
    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

/** Consulta payment method.
 * @return resultado de la operacion.
 */
    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

/** Actualiza payment method.
 * @param paymentMethod parametro de entrada.
 */
    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

/** Consulta currency.
 * @return resultado de la operacion.
 */
    public String getCurrency() {
        return currency;
    }

/** Actualiza currency.
 * @param currency parametro de entrada.
 */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

/** Consulta status.
 * @return resultado de la operacion.
 */
    public PaymentStatusEnum getStatus() {
        return status;
    }

/** Actualiza status.
 * @param status parametro de entrada.
 */
    public void setStatus(PaymentStatusEnum status) {
        this.status = status;
    }

/** Consulta created at.
 * @return resultado de la operacion.
 */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
