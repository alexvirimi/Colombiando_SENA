package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LanguageRepository extends JpaRepository<LanguageEntity, UUID> {
    public LanguageEntity findByCode(String code);
    public boolean existsByCode(String code);
}
