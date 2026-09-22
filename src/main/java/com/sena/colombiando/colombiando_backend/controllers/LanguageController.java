package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.LanguageDto;
import com.sena.colombiando.colombiando_backend.services.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    private final LanguageService languageService;
/** Inicializa la instancia.
 * @param languageService parametro de entrada.
 */
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

/** Consulta all.
 * @return resultado de la operacion.
 */
    @GetMapping
    public ResponseEntity<List<LanguageDto.Response>> getAll() {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

/** Consulta by id.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @GetMapping("/id")
    public ResponseEntity<LanguageDto.Response> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(languageService.getById(id));
    }

/** Ejecuta la operacion get mapping.
 * @param code parametro de entrada.
 */
/** Consulta by code.
 * @param code parametro de entrada.
 * @return resultado de la operacion.
 */
    @GetMapping("/by-code/{code}")
    public ResponseEntity<LanguageDto.Response> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(languageService.findByCode(code));
    }

/** Crea .
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @PostMapping
    public ResponseEntity<LanguageDto.Response> create(LanguageDto.Create request) {
        LanguageDto.Response created = languageService.createLanguage(request);
        return ResponseEntity.created(URI.create("/api/languages/" + created.id())).body(created);
    }

/** Elimina by id.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @DeleteMapping
    public ResponseEntity<LanguageDto.Response> deleteById(@PathVariable UUID id) {
        return ResponseEntity.ok(languageService.deleteLanguage(id));
    }

}
