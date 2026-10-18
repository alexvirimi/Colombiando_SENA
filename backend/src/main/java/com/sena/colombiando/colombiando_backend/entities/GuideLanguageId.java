package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class GuideLanguageId implements Serializable {

    @Column(name = "guide_id")
    private UUID guideId;

    @Column(name = "language_id")
    private UUID languageId;

/** Inicializa la instancia.
 */
    public GuideLanguageId() {}

    // Getters & Setters

/** Consulta guide id.
 * @return resultado de la operacion.
 */
    public UUID getGuideId() {
        return guideId;
    }

/** Actualiza guide id.
 * @param guideId parametro de entrada.
 */
    public void setGuideId(UUID guideId) {
        this.guideId = guideId;
    }

/** Consulta language id.
 * @return resultado de la operacion.
 */
    public UUID getLanguageId() {
        return languageId;
    }

/** Actualiza language id.
 * @param languageId parametro de entrada.
 */
    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    // Equals & Hashcode

/** Compara la entidad con otro objeto.
 * @param o parametro de entrada.
 * @return resultado de la operacion.
 */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuideLanguageId that = (GuideLanguageId) o;
        return Objects.equals(guideId, that.guideId) &&
                Objects.equals(languageId, that.languageId);
    }

/** Calcula el hash de la entidad.
 * @return resultado de la operacion.
 */
    @Override
    public int hashCode() {
        return Objects.hash(guideId, languageId);
    }
}
