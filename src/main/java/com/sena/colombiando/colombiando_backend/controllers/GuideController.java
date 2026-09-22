package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.GuideDto;
import com.sena.colombiando.colombiando_backend.entities.GuideStatusEnum;
import com.sena.colombiando.colombiando_backend.services.GuideService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/guides")
public class GuideController {

    private final GuideService guideService;
    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

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

    @PostMapping
    public ResponseEntity<GuideDto.Response> create(
            @Valid @RequestBody GuideDto.Create request
    ) {
        GuideDto.Response created = guideService.createGuide(request);
        return ResponseEntity.created(URI.create("/api/guides/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuideDto.Response> update(
            @PathVariable UUID id,
            @RequestBody GuideDto.Update request
    ) {
        return ResponseEntity.ok(guideService.updateGuide(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GuideDto.Response> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(guideService.deleteGuide(id));
    }

}
