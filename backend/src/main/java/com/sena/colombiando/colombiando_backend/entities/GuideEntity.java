package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "guides")
public class GuideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 150,  nullable = false)
    private String name;

    @Column(name = "last_name", length = 150, nullable = false)
    private String lastName;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 45)
    private String phone;

    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @org.hibernate.annotations.ColumnDefault("'ACTIVE'")
    private GuideStatusEnum status =  GuideStatusEnum.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

/** Inicializa la instancia.
 */
    public GuideEntity() {}

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

/** Consulta bio.
 * @return resultado de la operacion.
 */
    public String getBio() {
        return bio;
    }

/** Actualiza bio.
 * @param bio parametro de entrada.
 */
    public void setBio(String bio) {
        this.bio = bio;
    }

/** Consulta phone.
 * @return resultado de la operacion.
 */
    public String getPhone() {
        return phone;
    }

/** Actualiza phone.
 * @param phone parametro de entrada.
 */
    public void setPhone(String phone) {
        this.phone = phone;
    }

/** Consulta profile image.
 * @return resultado de la operacion.
 */
    public String getProfileImage() {
        return profileImage;
    }

/** Actualiza profile image.
 * @param profileImage parametro de entrada.
 */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

/** Consulta status.
 * @return resultado de la operacion.
 */
    public GuideStatusEnum getStatus() {
        return status;
    }

/** Actualiza status.
 * @param status parametro de entrada.
 */
    public void setStatus(GuideStatusEnum status) {
        this.status = status;
    }

/** Consulta created at.
 * @return resultado de la operacion.
 */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
