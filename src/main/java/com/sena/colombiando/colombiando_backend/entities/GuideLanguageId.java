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
    private String languageId;

    public GuideLanguageId() {}

    // Getters & Setters

    public UUID getGuideId() {
        return guideId;
    }

    public void setGuideId(UUID guideId) {
        this.guideId = guideId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    // Equals & Hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuideLanguageId that = (GuideLanguageId) o;
        return Objects.equals(guideId, that.guideId) &&
                Objects.equals(languageId, that.languageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guideId, languageId);
    }
}
