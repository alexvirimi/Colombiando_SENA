package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.GuideLanguageDto;
import com.sena.colombiando.colombiando_backend.entities.GuideLanguageEntity;
import com.sena.colombiando.colombiando_backend.entities.GuideLanguageId;
import com.sena.colombiando.colombiando_backend.services.GuideLanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/guide-languages")
@Tag(name = "Guide Languages", description = "Gestión de idiomas de los guías")
public class GuideLanguageController {

    private final GuideLanguageService guideLanguageService;
    /** Inicializa la instancia.
     * @param guideLanguageService parametro de entrada.
     */
    public GuideLanguageController(GuideLanguageService guideLanguageService) {
        this.guideLanguageService = guideLanguageService;
    }

    /** Consulta all.
     * @param guideId parametro de entrada.
     * @param languageId parametro de entrada.
     * @return resultado de la operacion.
     */
    @Operation(summary = "Listar idiomas de guías")
    @ApiResponse(responseCode = "200", description = "Relaciones consultadas correctamente.")
    @GetMapping
    public ResponseEntity<List<GuideLanguageDto.Response>> getAll(
            @RequestParam(required = false) UUID guideId,
            @RequestParam(required = false) UUID languageId
    ) {
        return ResponseEntity.ok(guideLanguageService.searchGuideLanguages(guideId, languageId));
    }

    /** Crea .
     * @param request parametro de entrada.
     * @return resultado de la operacion.
     */
    @Operation(summary = "Asignar idioma a un guía")
    @ApiResponse(responseCode = "201", description = "Idioma asignado correctamente.")
    @PostMapping
    public ResponseEntity<GuideLanguageDto.Response> create(
            @RequestBody GuideLanguageDto.Create request
    ) {
        GuideLanguageDto.Response created = guideLanguageService.createGuideLanguage(request);
        return ResponseEntity.created(URI.create("/api/guide-languages/" + created.id())).body(created);
    }

    /** Ejecuta la operacion get mapping.
     * @param languageId parametro de entrada.
     */
    /** Consulta by guide and language.
     * @param guideId parametro de entrada.
     * @param languageId parametro de entrada.
     * @return resultado de la operacion.
     */
    @Operation(summary = "Consultar relación por guía e idioma")
    @ApiResponse(responseCode = "200", description = "Relación consultada correctamente.")
    @GetMapping("/by-guide/{guideId}/by-language/{languageId}")
    public ResponseEntity<GuideLanguageDto.Response> getByGuideAndLanguage(
            @PathVariable UUID guideId,
            @PathVariable UUID languageId
    ) {
        return ResponseEntity.ok(guideLanguageService
                .getGuideLanguageByGuideIdAndLanguageId(guideId, languageId)
        );
    }

    /** Ejecuta la operacion patch mapping.
     * @param id parametro de entrada.
     */
    /** Actualiza .
     * @param guideId parametro de entrada.
     * @param languageId parametro de entrada.
     * @param request parametro de entrada.
     * @return resultado de la operacion.
     */
    @Operation(summary = "Actualizar relación por guía e idioma")
    @ApiResponse(responseCode = "200", description = "Relación actualizada correctamente.")
    @PatchMapping("/by-guide/{guideId}/by-language/{languageId}")
    public ResponseEntity<GuideLanguageDto.Response> updateByGuideAndLanguage(
            @PathVariable UUID guideId, @PathVariable UUID languageId,
            @RequestBody GuideLanguageDto.Update request
    ) {
        return ResponseEntity.ok(guideLanguageService.updateGuideLanguageByGuideIdAndLanguageId(
                guideId, languageId, request
        ));
    }

    /** Ejecuta la operacion delete mapping.
     * @param languageId parametro de entrada.
     */
    /** Elimina by guide and language.
     * @param guideId parametro de entrada.
     * @param languageId parametro de entrada.
     * @return resultado de la operacion.
     */
    @Operation(summary = "Eliminar relación por guía e idioma")
    @ApiResponse(responseCode = "200", description = "Relación eliminada correctamente.")
    @DeleteMapping("/by-guide/{guideId}/by-language/{languageId}")
    public ResponseEntity<GuideLanguageDto.Response> deleteByGuideAndLanguage(
            @PathVariable UUID guideId,
            @PathVariable UUID languageId
    ) {
        return ResponseEntity.ok(guideLanguageService
                .deleteGuideLanguageByGuideIdAndLanguageId(guideId, languageId)
        );
    }

}
