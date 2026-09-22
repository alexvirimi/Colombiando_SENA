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
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<List<LanguageDto.Response>> getAll() {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    @GetMapping("/id")
    public ResponseEntity<LanguageDto.Response> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(languageService.getById(id));
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<LanguageDto.Response> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(languageService.findByCode(code));
    }

    @PostMapping
    public ResponseEntity<LanguageDto.Response> create(LanguageDto.Create request) {
        LanguageDto.Response created = languageService.createLanguage(request);
        return ResponseEntity.created(URI.create("/api/languages/" + created.id())).body(created);
    }

    @DeleteMapping
    public ResponseEntity<LanguageDto.Response> deleteById(@PathVariable UUID id) {
        return ResponseEntity.ok(languageService.deleteLanguage(id));
    }

}
