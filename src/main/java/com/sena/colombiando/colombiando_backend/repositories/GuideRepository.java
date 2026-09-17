package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.GuideEntity;
import com.sena.colombiando.colombiando_backend.entities.GuideStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface GuideRepository extends JpaRepository<GuideEntity, UUID> {
    public List<GuideEntity> findByStatus(GuideStatusEnum status);
    List<GuideEntity> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(String name, String lastName);
}
