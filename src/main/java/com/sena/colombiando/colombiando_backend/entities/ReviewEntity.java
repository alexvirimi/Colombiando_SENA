package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "reviews",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_review_booking",
                        columnNames = {"booking_id"}
                )
        }
)
public class ReviewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_review_user",
                    foreignKeyDefinition = "FOREIGN KEY user_id " +
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
                    foreignKeyDefinition = "FOREIGN KEY booking_id " +
                            "REFERENCES bookings(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    private BookingEntity booking;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Integer rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String review;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ReviewsEntity() {}

    // Getters & Setters

    public UUID getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
