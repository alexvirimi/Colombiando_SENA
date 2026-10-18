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
    @Column(name = "document_type", nullable = false)
    private UserDocumentTypeEnum documentType;

    @Column(name = "id_number", length = 45, nullable = false)
    private String idNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

/** Inicializa la instancia.
 */
    public BookingEscortEntity() {}

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

/** Consulta name.
 * @return resultado de la operacion.
 */
    public String getName() {
        return name;
    }

/** Actualiza name.
 * @param name parametro de entrada.
 */
    public void setName(String name) {
        this.name = name;
    }

/** Consulta last name.
 * @return resultado de la operacion.
 */
    public String getLastName() {
        return lastName;
    }

/** Actualiza last name.
 * @param lastName parametro de entrada.
 */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

/** Consulta document type.
 * @return resultado de la operacion.
 */
    public UserDocumentTypeEnum getDocumentType() {
        return documentType;
    }

/** Actualiza document type.
 * @param documentType parametro de entrada.
 */
    public void setDocumentType(UserDocumentTypeEnum documentType) {
        this.documentType = documentType;
    }

/** Consulta id number.
 * @return resultado de la operacion.
 */
    public String getIdNumber() {
        return idNumber;
    }

/** Actualiza id number.
 * @param idNumber parametro de entrada.
 */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

/** Consulta birth date.
 * @return resultado de la operacion.
 */
    public LocalDate getBirthDate() {
        return birthDate;
    }

/** Actualiza birth date.
 * @param birthDate parametro de entrada.
 */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
