package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "reviews",
        uniqueConstraints = {
/** Ejecuta la operacion unique constraint.
 * @param uq_review_booking parametro de entrada.
 * @param booking_id parametro de entrada.
 */
                @UniqueConstraint(
                        name = "uq_review_booking",
                        columnNames = {"booking_id"}
                )
        }
)
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_review_user",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) " +
                            "REFERENCES users(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "booking_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_review_booking",
                    foreignKeyDefinition = "FOREIGN KEY (booking_id) " +
                            "REFERENCES bookings(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    private BookingEntity booking;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private int rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String review;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

/** Inicializa la instancia.
 */
    public ReviewEntity() {}

    // Getters & Setters

/** Consulta id.
 * @return resultado de la operacion.
 */
    public UUID getId() {
        return id;
    }

/** Consulta user.
 * @return resultado de la operacion.
 */
    public UserEntity getUser() {
        return user;
    }

/** Actualiza user.
 * @param user parametro de entrada.
 */
    public void setUser(UserEntity user) {
        this.user = user;
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

/** Consulta rating.
 * @return resultado de la operacion.
 */
    public int getRating() {
        return rating;
    }

/** Actualiza rating.
 * @param rating parametro de entrada.
 */
    public void setRating(int rating) {
        this.rating = rating;
    }

/** Consulta review.
 * @return resultado de la operacion.
 */
    public String getReview() {
        return review;
    }

/** Actualiza review.
 * @param review parametro de entrada.
 */
    public void setReview(String review) {
        this.review = review;
    }

/** Consulta created at.
 * @return resultado de la operacion.
 */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
