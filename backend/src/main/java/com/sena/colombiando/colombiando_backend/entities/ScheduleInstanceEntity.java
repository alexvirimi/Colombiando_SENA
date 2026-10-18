package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "schedule_instances",
        indexes = {
                @Index(
                        name = "idx_instace_date",
                        columnList = "date"
                )
        },
        uniqueConstraints = {
/** Ejecuta la operacion unique constraint.
 * @param uq_instance_schedule_date parametro de entrada.
 * @param schedule_id parametro de entrada.
 * @param date parametro de entrada.
 */
                @UniqueConstraint(
                        name = "uq_instance_schedule_date",
                        columnNames = {"schedule_id", "date"}
                )
        }
)
public class ScheduleInstanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "schedule_id",
            foreignKey = @ForeignKey(
                    name = "fk_schedule",
                    foreignKeyDefinition = "FOREIGN KEY (schedule_id) " +
                            "REFERENCES schedules(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    private ScheduleEntity schedule;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date;

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity = 1;

    @Column(name = "available_capacity", nullable = false)
    private int availableCapacity = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    @org.hibernate.annotations.ColumnDefault("'ACTIVE'")
    private ScheduleInstanceStateEnum state =  ScheduleInstanceStateEnum.ACTIVE;

/** Consulta id.
 * @return resultado de la operacion.
 */
    public UUID getId() {
        return id;
    }

/** Consulta schedule.
 * @return resultado de la operacion.
 */
    public ScheduleEntity getSchedule() {
        return schedule;
    }

/** Actualiza schedule.
 * @param schedule parametro de entrada.
 */
    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }

/** Consulta date.
 * @return resultado de la operacion.
 */
    public LocalDate getDate() {
        return date;
    }

/** Actualiza date.
 * @param date parametro de entrada.
 */
    public void setDate(LocalDate date) {
        this.date = date;
    }

/** Consulta max capacity.
 * @return resultado de la operacion.
 */
    public int getMaxCapacity() {
        return maxCapacity;
    }

/** Actualiza max capacity.
 * @param maxCapacity parametro de entrada.
 */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

/** Consulta available capacity.
 * @return resultado de la operacion.
 */
    public int getAvailableCapacity() {
        return availableCapacity;
    }

/** Actualiza available capacity.
 * @param availableCapacity parametro de entrada.
 */
    public void setAvailableCapacity(int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

/** Consulta state.
 * @return resultado de la operacion.
 */
    public ScheduleInstanceStateEnum getState() {
        return state;
    }

/** Actualiza state.
 * @param state parametro de entrada.
 */
    public void setState(ScheduleInstanceStateEnum state) {
        this.state = state;
    }
}
