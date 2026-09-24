package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.GuideDto;
import com.sena.colombiando.colombiando_backend.entities.GuideStatusEnum;
import com.sena.colombiando.colombiando_backend.services.GuideService;
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
@RequestMapping("/guides")
@Tag(name = "Guides", description = "Gestión de guías turísticos")
public class GuideController {

    private final GuideService guideService;
/** Inicializa la instancia.
 * @param guideService parametro de entrada.
 */
    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

/** Consulta find all.
 * @param name parametro de entrada.
 * @param lastName parametro de entrada.
 * @param guideStatus parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Listar guías", description = "Consulta guías con filtros opcionales.")
    @ApiResponse(responseCode = "200", description = "Guías consultados correctamente.")
    @GetMapping
    public ResponseEntity<List<GuideDto.Response>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false)GuideStatusEnum guideStatus
            ) {
        if (name != null && lastName != null) {
            return ResponseEntity.ok(
                    guideService.getGuideByNameAndLastName(name, lastName)
            );
        }
        if (guideStatus != null) {
            return ResponseEntity.ok(
                    guideService.getGuideByStatus(guideStatus)
            );
        }
        return ResponseEntity.ok(guideService.GetAllGuides());
    }

/** Crea .
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Crear guía")
    @ApiResponse(responseCode = "201", description = "Guía creado correctamente.")
    @PostMapping
    public ResponseEntity<GuideDto.Response> create(
            @Valid @RequestBody GuideDto.Create request
    ) {
        GuideDto.Response created = guideService.createGuide(request);
        return ResponseEntity.created(URI.create("/api/guides/" + created.id())).body(created);
    }

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta payment.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Consultar guía por ID")
    @ApiResponse(responseCode = "200", description = "Guía consultado correctamente.")
    @GetMapping("/{id}")
    public ResponseEntity<GuideDto.Response> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(guideService.getGuide(id));
    }

/** Ejecuta la operacion patch mapping.
 * @param id parametro de entrada.
 */
/** Actualiza .
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Actualizar guía")
    @ApiResponse(responseCode = "200", description = "Guía actualizado correctamente.")
    @PatchMapping("/{id}")
    public ResponseEntity<GuideDto.Response> update(
            @PathVariable UUID id,
            @RequestBody GuideDto.Update request
    ) {
        return ResponseEntity.ok(guideService.updateGuide(id, request));
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina .
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Eliminar guía")
    @ApiResponse(responseCode = "200", description = "Guía eliminado correctamente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<GuideDto.Response> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(guideService.deleteGuide(id));
    }

}
