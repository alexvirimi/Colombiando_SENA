package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "languages")
public class LanguagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 2, columnDefinition = "CHAR(2)", nullable = false, unique = true)
    private String code;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(name = "native_name", length = 100, nullable = false)
    private String nativeName;

    public LanguagesEntity() {}

    // Getters & Setters

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
}
