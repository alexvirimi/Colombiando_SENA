package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.GuideLanguageDto;
import com.sena.colombiando.colombiando_backend.entities.GuideLanguageEntity;
import com.sena.colombiando.colombiando_backend.entities.GuideLanguageId;
import com.sena.colombiando.colombiando_backend.services.GuideLanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/guide-languages")
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
    @PostMapping
    public ResponseEntity<GuideLanguageDto.Response> create(
            @RequestBody GuideLanguageDto.Create request
    ) {
        GuideLanguageDto.Response created = guideLanguageService.createGuideLanguage(request);
        return ResponseEntity.created(URI.create("/api/guide-languages/" + created.id())).body(created);
    }

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta one.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @GetMapping("/{id}")
    public ResponseEntity<GuideLanguageDto.Response> getOne(
            @PathVariable GuideLanguageId id
    ) {
        return ResponseEntity.ok(guideLanguageService.getGuideLanguage(id));
    }

/** Ejecuta la operacion get mapping.
 * @param languageId parametro de entrada.
 */
/** Consulta by guide and language.
 * @param guideId parametro de entrada.
 * @param languageId parametro de entrada.
 * @return resultado de la operacion.
 */
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
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @PatchMapping("/{id}")
    public ResponseEntity<GuideLanguageDto.Response> update(
            @PathVariable GuideLanguageId id,
            @RequestBody GuideLanguageDto.Update request
    ) {
        return ResponseEntity.ok(guideLanguageService.updateGuideLanguage(
                id, request
        ));
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina .
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @DeleteMapping("/{id}")
    public ResponseEntity<GuideLanguageDto.Response> delete(
            @PathVariable GuideLanguageId id
    ) {
        return ResponseEntity.ok(guideLanguageService.deleteGuideLanguage(id));
    }

/** Ejecuta la operacion delete mapping.
 * @param languageId parametro de entrada.
 */
/** Elimina by guide and language.
 * @param guideId parametro de entrada.
 * @param languageId parametro de entrada.
 * @return resultado de la operacion.
 */
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
