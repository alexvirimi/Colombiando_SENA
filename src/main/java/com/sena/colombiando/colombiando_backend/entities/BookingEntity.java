package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "bookings",
        indexes = {
                @Index(
                        name = "idx_schedule_instances",
                        columnList = "instance_id"
                )
        }
)
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_booking_user",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) " +
                            "REFERENCES users(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "instance_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_booking_instance",
                    foreignKeyDefinition = "FOREIGN KEY (instance_id) " +
                            "REFERENCES schedule_instances(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    private ScheduleInstanceEntity scheduleInstance;

    @Column(name = "num_people", nullable = false)
    private int numPeople;

    @Column(name = "total_price", precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @org.hibernate.annotations.ColumnDefault("'PENDING'")
    private BookingStatusEnum status = BookingStatusEnum.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public BookingEntity() {}

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

    public ScheduleInstanceEntity getScheduleInstance() {
        return scheduleInstance;
    }

    public void setScheduleInstance(ScheduleInstanceEntity scheduleInstance) {
        this.scheduleInstance = scheduleInstance;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BookingStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
