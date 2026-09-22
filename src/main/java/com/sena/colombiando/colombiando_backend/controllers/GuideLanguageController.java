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
    public GuideLanguageController(GuideLanguageService guideLanguageService) {
        this.guideLanguageService = guideLanguageService;
    }

    @GetMapping
    public ResponseEntity<List<GuideLanguageDto.Response>> getAll(
            @RequestParam(required = false) UUID guideId,
            @RequestParam(required = false) UUID languageId
    ) {
        return ResponseEntity.ok(guideLanguageService.searchGuideLanguages(guideId, languageId));
    }

    @PostMapping
    public ResponseEntity<GuideLanguageDto.Response> create(
            @RequestBody GuideLanguageDto.Create request
    ) {
        GuideLanguageDto.Response created = guideLanguageService.createGuideLanguage(request);
        return ResponseEntity.created(URI.create("/api/guide-languages/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuideLanguageDto.Response> getOne(
            @PathVariable GuideLanguageId id
    ) {
        return ResponseEntity.ok(guideLanguageService.getGuideLanguage(id));
    }

    @GetMapping("/by-guide/{guideId}/by-language/{languageId}")
    public ResponseEntity<GuideLanguageDto.Response> getByGuideAndLanguage(
            @PathVariable UUID guideId,
            @PathVariable UUID languageId
    ) {
        return ResponseEntity.ok(guideLanguageService
                .getGuideLanguageByGuideIdAndLanguageId(guideId, languageId)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GuideLanguageDto.Response> update(
            @PathVariable GuideLanguageId id,
            @RequestBody GuideLanguageDto.Update request
    ) {
        return ResponseEntity.ok(guideLanguageService.updateGuideLanguage(
                id, request
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GuideLanguageDto.Response> delete(
            @PathVariable GuideLanguageId id
    ) {
        return ResponseEntity.ok(guideLanguageService.deleteGuideLanguage(id));
    }

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
