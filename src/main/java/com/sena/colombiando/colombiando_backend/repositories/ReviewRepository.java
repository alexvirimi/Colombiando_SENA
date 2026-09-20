package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface    ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    public List<ReviewEntity> findByBookingId(UUID id);
    public List<ReviewEntity> findByUserId(UUID id);
    public List<ReviewEntity> findByRatingGreaterThan(int rating);

    @Query("SELECT COALESCE(AVG(r.rating), 0.0) FROM ReviewEntity r " +
            "JOIN r.booking b " +
            "JOIN b.scheduleInstance i " +
            "JOIN i.schedule s " +
            "JOIN s.guide g " +
            "WHERE g.id = :guideId")
    Double averageRatingByGuide(@Param("guideId") UUID guideId);
}
