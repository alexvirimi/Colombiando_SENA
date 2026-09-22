package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "guide_languages")
public class GuideLanguageEntity {

/** Ejecuta la operacion guide language id.
 * @return resultado de la operacion.
 */
    @EmbeddedId
    private GuideLanguageId id = new GuideLanguageId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("guideId")
    @JoinColumn(
            name = "guide_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_guidelanguages_guide",
                    foreignKeyDefinition = "FOREIGN KEY (guide_id) " +
                            "REFERENCES guides(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE CASCADE"
            )
    )
    private GuideEntity guide;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("languageId")
    @JoinColumn(
            name = "language_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_guidelanguages_language",
                    foreignKeyDefinition = "FOREIGN KEY (language_id) " +
                            "REFERENCES languages(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE CASCADE"
            )
    )
    private LanguageEntity language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @org.hibernate.annotations.ColumnDefault("'NATIVE'")
    private GuideLanguagesLevelEnum level = GuideLanguagesLevelEnum.NATIVE;

/** Inicializa la instancia.
 */
    public GuideLanguageEntity() {}

/** Consulta id.
 * @return resultado de la operacion.
 */
    public GuideLanguageId getId() {
        return id;
    }

/** Actualiza id.
 * @param id parametro de entrada.
 */
    public void setId(GuideLanguageId id) {
        this.id = id;
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
        this.id.setGuideId(guide != null ? guide.getId() : null);
    }

/** Consulta language.
 * @return resultado de la operacion.
 */
    public LanguageEntity getLanguage() {
        return language;
    }

/** Actualiza language.
 * @param language parametro de entrada.
 */
    public void setLanguage(LanguageEntity language) {
        this.language = language;
        this.id.setLanguageId(language != null ? language.getId() : null);
    }

/** Consulta level.
 * @return resultado de la operacion.
 */
    public GuideLanguagesLevelEnum getLevel() {
        return level;
    }

/** Actualiza level.
 * @param level parametro de entrada.
 */
    public void setLevel(GuideLanguagesLevelEnum level) {
        this.level = level;
    }
}
