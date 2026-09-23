package com.sena.colombiando.colombiando_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 150)
    private String name;

    @Column(name = "last_name", length = 150)
    private String lastName; // Changed to camelCase to follow Java naming conventions

    @Column(length = 150, unique = true, nullable = false)
    private String email;

    @Column(length = 45)
    private String phone;

    @Column(length = 255,  nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @org.hibernate.annotations.ColumnDefault("'ACTIVE'")
    private UserStatusEnum status = UserStatusEnum.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

/** Inicializa la instancia.
 */
    public UserEntity() {}

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

/** Consulta email.
 * @return resultado de la operacion.
 */
    public String getEmail() {
        return email;
    }

/** Actualiza email.
 * @param email parametro de entrada.
 */
    public void setEmail(String email) {
        this.email = email;
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

/** Consulta password.
 * @return resultado de la operacion.
 */
    public String getPassword() {
        return password;
    }

/** Consulta status.
 * @return resultado de la operacion.
 */
    public UserStatusEnum getStatus() {
        return status;
    }

/** Actualiza status.
 * @param status parametro de entrada.
 */
    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }

/** Actualiza password.
 * @param password parametro de entrada.
 */
    public void setPassword(String password) {
        this.password = password;
    }

/** Consulta created at.
 * @return resultado de la operacion.
 */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
