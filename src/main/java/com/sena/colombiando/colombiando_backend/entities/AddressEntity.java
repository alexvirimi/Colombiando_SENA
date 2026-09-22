package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "addresses")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "address_text", columnDefinition = "TEXT", nullable = false)
    private String addressText;

    @Column(name = "country_code", columnDefinition = "CHAR(2)", length = 2)
    private String countryCode;

    @Column(length = 100)
    private String municipality;

    @Column(precision = 9, scale = 6,  nullable = false)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6,  nullable = false)
    private BigDecimal longitude;

    // Constructors

/** Inicializa la instancia.
 */
    public AddressEntity() {}

    // Getters & Setters

/** Consulta id.
 * @return resultado de la operacion.
 */
    public UUID getId() {
        return id;
    }

/** Consulta address text.
 * @return resultado de la operacion.
 */
    public String getAddressText() {
        return addressText;
    }

/** Actualiza address text.
 * @param addressText parametro de entrada.
 */
    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

/** Consulta country code.
 * @return resultado de la operacion.
 */
    public String getCountryCode() {
        return countryCode;
    }

/** Actualiza country code.
 * @param countryCode parametro de entrada.
 */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

/** Consulta municipality.
 * @return resultado de la operacion.
 */
    public String getMunicipality() {
        return municipality;
    }

/** Actualiza municipality.
 * @param municipality parametro de entrada.
 */
    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

/** Consulta latitude.
 * @return resultado de la operacion.
 */
    public BigDecimal getLatitude() {
        return latitude;
    }

/** Actualiza latitude.
 * @param latitude parametro de entrada.
 */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

/** Consulta longitude.
 * @return resultado de la operacion.
 */
    public BigDecimal getLongitude() {
        return longitude;
    }

/** Actualiza longitude.
 * @param longitude parametro de entrada.
 */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

}
