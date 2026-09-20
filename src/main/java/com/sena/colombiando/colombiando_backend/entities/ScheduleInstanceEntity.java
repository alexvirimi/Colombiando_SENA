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
            nullable = false,
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

    public UUID getId() {
        return id;
    }

    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public ScheduleInstanceStateEnum getState() {
        return state;
    }

    public void setState(ScheduleInstanceStateEnum state) {
        this.state = state;
    }
}
