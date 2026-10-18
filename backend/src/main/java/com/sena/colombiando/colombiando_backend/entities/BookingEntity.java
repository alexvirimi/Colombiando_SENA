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

/** Inicializa la instancia.
 */
    public BookingEntity() {}

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

/** Consulta schedule instance.
 * @return resultado de la operacion.
 */
    public ScheduleInstanceEntity getScheduleInstance() {
        return scheduleInstance;
    }

/** Actualiza schedule instance.
 * @param scheduleInstance parametro de entrada.
 */
    public void setScheduleInstance(ScheduleInstanceEntity scheduleInstance) {
        this.scheduleInstance = scheduleInstance;
    }

/** Consulta num people.
 * @return resultado de la operacion.
 */
    public int getNumPeople() {
        return numPeople;
    }

/** Actualiza num people.
 * @param numPeople parametro de entrada.
 */
    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

/** Consulta total price.
 * @return resultado de la operacion.
 */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

/** Actualiza total price.
 * @param totalPrice parametro de entrada.
 */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

/** Consulta status.
 * @return resultado de la operacion.
 */
    public BookingStatusEnum getStatus() {
        return status;
    }

/** Actualiza status.
 * @param status parametro de entrada.
 */
    public void setStatus(BookingStatusEnum status) {
        this.status = status;
    }

/** Consulta created at.
 * @return resultado de la operacion.
 */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
