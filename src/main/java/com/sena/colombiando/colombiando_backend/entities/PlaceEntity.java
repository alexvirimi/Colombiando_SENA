package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "places")
public class PlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "address_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_places_address",
                    foreignKeyDefinition = "FOREIGN KEY (address_id) " +
                            "REFERENCES addresses(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    private AddressEntity address;

    @Column(columnDefinition = "TEXT")
    private String headerImg;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

/** Inicializa la instancia.
 */
    public PlaceEntity() {}

    // Getters & Setters

/** Consulta id.
 * @return resultado de la operacion.
 */
    public UUID getId() {
        return id;
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

/** Consulta description.
 * @return resultado de la operacion.
 */
    public String getDescription() {
        return description;
    }

/** Actualiza description.
 * @param description parametro de entrada.
 */
    public void setDescription(String description) {
        this.description = description;
    }

/** Consulta address.
 * @return resultado de la operacion.
 */
    public AddressEntity getAddress() {
        return address;
    }

/** Actualiza address.
 * @param address parametro de entrada.
 */
    public void setAddress(AddressEntity address) {
        this.address = address;
    }

/** Consulta header img.
 * @return resultado de la operacion.
 */
    public String getHeaderImg() {
        return headerImg;
    }

/** Actualiza header img.
 * @param headerImg parametro de entrada.
 */
    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

/** Consulta created at.
 * @return resultado de la operacion.
 */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
