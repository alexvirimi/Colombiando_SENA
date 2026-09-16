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

    public AddressEntity() {}

    // Getters & Setters

    public UUID getId() {
        return id;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

}
