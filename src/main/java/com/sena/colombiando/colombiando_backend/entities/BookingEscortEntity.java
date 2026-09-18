package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "booking_escorts")
public class BookingEscortEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "booking_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_booking_escorts_booking"
            )
    )
    private BookingEntity booking;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(name = "last_name", length = 150, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_type", nullable = false)
    private UserDocumentTypeEnum status;

    @Column(name = "id_number", length = 45)
    private String idNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    public BookingEscortEntity() {}

    // Getters & Setters

    public UUID getId() {
        return id;
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserDocumentTypeEnum getStatus() {
        return status;
    }

    public void setStatus(UserDocumentTypeEnum status) {
        this.status = status;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
