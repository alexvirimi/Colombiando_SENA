package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "languages")
public class LanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 2, columnDefinition = "CHAR(2)", nullable = false, unique = true)
    private String code;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(name = "native_name", length = 100, nullable = false)
    private String nativeName;

/** Inicializa la instancia.
 */
    public LanguageEntity() {}

    // Getters & Setters

/** Consulta id.
 * @return resultado de la operacion.
 */
    public UUID getId() {
        return id;
    }

/** Consulta code.
 * @return resultado de la operacion.
 */
    public String getCode() {
        return code;
    }

/** Actualiza code.
 * @param code parametro de entrada.
 */
    public void setCode(String code) {
        this.code = code;
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

/** Consulta native name.
 * @return resultado de la operacion.
 */
    public String getNativeName() {
        return nativeName;
    }

/** Actualiza native name.
 * @param nativeName parametro de entrada.
 */
    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
}
