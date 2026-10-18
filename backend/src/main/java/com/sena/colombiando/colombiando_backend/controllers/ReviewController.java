package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.ReviewDto;
import com.sena.colombiando.colombiando_backend.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Reviews", description = "Gestión de reseñas")
public class ReviewController {

    private final ReviewService reviewService;
/** Inicializa la instancia.
 * @param reviewService parametro de entrada.
 */
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

/** Consulta all reviews.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Listar reseñas")
    @ApiResponse(responseCode = "200", description = "Reseñas consultadas correctamente.")
    @GetMapping
    public ResponseEntity<List<ReviewDto.Response>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

/** Crea .
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Crear reseña")
    @ApiResponse(responseCode = "201", description = "Reseña creada correctamente.")
    @PostMapping
    public ResponseEntity<ReviewDto.Response> create(
            @Valid @RequestBody ReviewDto.Create request
    ) {
        ReviewDto.Response created = reviewService.createReview(request);
        return ResponseEntity.created(URI.create("/api/reviews/" + created.id())).body(created);
    }

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta review by id.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Consultar reseña por ID")
    @ApiResponse(responseCode = "200", description = "Reseña consultada correctamente.")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto.Response> getReviewById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

/** Ejecuta la operacion patch mapping.
 * @param id parametro de entrada.
 */
/** Actualiza .
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Actualizar reseña")
    @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente.")
    @PatchMapping("/{id}")
    public ResponseEntity<ReviewDto.Response> update(
            @PathVariable UUID id,
            @RequestBody ReviewDto.Update request
    ) {
        return ResponseEntity.ok(reviewService.updateReview(id, request));
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina review by id.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Eliminar reseña")
    @ApiResponse(responseCode = "200", description = "Reseña eliminada correctamente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDto.Response> deleteReviewById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.deleteReview(id));
    }

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta average rating.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Consultar calificación promedio")
    @ApiResponse(responseCode = "200", description = "Calificación promedio consultada correctamente.")
    @GetMapping("/average-rating/{id}")
    public ResponseEntity<Double> getAverageRating(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(reviewService.getAverageRating(id));
    }

}
