package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.ReviewDto;
import com.sena.colombiando.colombiando_backend.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto.Response>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @PostMapping
    public ResponseEntity<ReviewDto.Response> create(
            @Valid @RequestBody ReviewDto.Create request
    ) {
        ReviewDto.Response created = reviewService.createReview(request);
        return ResponseEntity.created(URI.create("/api/reviews/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto.Response> getReviewById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReviewDto.Response> update(
            @PathVariable UUID id,
            @RequestBody ReviewDto.Update request
    ) {
        return ResponseEntity.ok(reviewService.updateReview(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDto.Response> deleteReviewById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.deleteReview(id));
    }

    @GetMapping("/average-rating/{id}")
    public ResponseEntity<Double> getAverageRating(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(reviewService.getAverageRating(id));
    }

}
