package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(
        name = "schedules",
        indexes = {
                @Index(
                        name = "idx_schedules_guide_dates",
                        columnList = "guide_id, start_date"
                ),
                @Index(
                        name = "idx_schedules_places_dates",
                        columnList = "place_id, start_date"
                )
        }
)
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "guide_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_schedule_guide",
                    foreignKeyDefinition = "FOREIGN KEY (guide_id) " +
                            "REFERENCES guides(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    private GuideEntity guide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "place_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_schedule_place",
                    foreignKeyDefinition = "FOREIGN KEY (place_id) " +
                            "REFERENCES places(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    @org.hibernate.annotations.OnDelete(
            action = org.hibernate.annotations.OnDeleteAction.RESTRICT
    )
    private PlaceEntity place;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity = 1;

    @Column(name = "price_per_person", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerPerson;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @org.hibernate.annotations.ColumnDefault("'ACTIVE'")
    private ScheduleStatusEnum status;

    public ScheduleEntity() {}

    // Getters & Setters

    public UUID getId() {
        return id;
    }

    public GuideEntity getGuide() {
        return guide;
    }

    public void setGuide(GuideEntity guide) {
        this.guide = guide;
    }

    public PlaceEntity getPlace() {
        return place;
    }

    public void setPlace(PlaceEntity place) {
        this.place = place;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public BigDecimal getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(BigDecimal pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public ScheduleStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatusEnum status) {
        this.status = status;
    }
}
