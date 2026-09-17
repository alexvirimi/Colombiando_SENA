package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.LanguagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LanguageRepository extends JpaRepository<LanguagesEntity, UUID> {
    public List<LanguagesEntity> findByCode(String code);
    public boolean exitsByCode(String code);
}
