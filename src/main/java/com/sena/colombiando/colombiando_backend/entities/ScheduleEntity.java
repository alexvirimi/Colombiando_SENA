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
    private ScheduleStatusEnum status = ScheduleStatusEnum.ACTIVE;

/** Inicializa la instancia.
 */
    public ScheduleEntity() {}

    // Getters & Setters

/** Consulta id.
 * @return resultado de la operacion.
 */
    public UUID getId() {
        return id;
    }

/** Consulta guide.
 * @return resultado de la operacion.
 */
    public GuideEntity getGuide() {
        return guide;
    }

/** Actualiza guide.
 * @param guide parametro de entrada.
 */
    public void setGuide(GuideEntity guide) {
        this.guide = guide;
    }

/** Consulta place.
 * @return resultado de la operacion.
 */
    public PlaceEntity getPlace() {
        return place;
    }

/** Actualiza place.
 * @param place parametro de entrada.
 */
    public void setPlace(PlaceEntity place) {
        this.place = place;
    }

/** Consulta start time.
 * @return resultado de la operacion.
 */
    public LocalTime getStartTime() {
        return startTime;
    }

/** Actualiza start time.
 * @param startTime parametro de entrada.
 */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

/** Consulta end time.
 * @return resultado de la operacion.
 */
    public LocalTime getEndTime() {
        return endTime;
    }

/** Actualiza end time.
 * @param endTime parametro de entrada.
 */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

/** Consulta start date.
 * @return resultado de la operacion.
 */
    public LocalDate getStartDate() {
        return startDate;
    }

/** Actualiza start date.
 * @param startDate parametro de entrada.
 */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

/** Consulta end date.
 * @return resultado de la operacion.
 */
    public LocalDate getEndDate() {
        return endDate;
    }

/** Actualiza end date.
 * @param endDate parametro de entrada.
 */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

/** Consulta price per person.
 * @return resultado de la operacion.
 */
    public BigDecimal getPricePerPerson() {
        return pricePerPerson;
    }

/** Actualiza price per person.
 * @param pricePerPerson parametro de entrada.
 */
    public void setPricePerPerson(BigDecimal pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

/** Consulta status.
 * @return resultado de la operacion.
 */
    public ScheduleStatusEnum getStatus() {
        return status;
    }

/** Actualiza status.
 * @param status parametro de entrada.
 */
    public void setStatus(ScheduleStatusEnum status) {
        this.status = status;
    }
}
