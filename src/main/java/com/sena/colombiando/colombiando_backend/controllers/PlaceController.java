package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.PlaceDto;
import com.sena.colombiando.colombiando_backend.services.PlaceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/places/")
public class PlaceController {

    private final PlaceService placeService;
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<List<PlaceDto.Response>> findAll(
            @RequestParam(required = false) String containing
    ) {
        if (containing != null) {
            return ResponseEntity.ok(placeService.getPlaceByNameContaining(containing));
        }
        return ResponseEntity.ok(placeService.getAllPlaces());
    }

    @PostMapping
    public ResponseEntity<PlaceDto.Response> create(
            @Valid @RequestBody PlaceDto.Create request
    ) {
        PlaceDto.Response created = placeService.createPlace(request);
        return ResponseEntity.created(URI.create("/api/places/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto.Response> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(placeService.getPlace(id));
    }

    @GetMapping("/by-address/{id}")
    public ResponseEntity<PlaceDto.Response> findByAddress(@PathVariable UUID addressId) {
        return ResponseEntity.ok(placeService.getPlaceByAddressId(addressId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlaceDto.Response> update(
            @PathVariable UUID id,
            @RequestBody PlaceDto.Update request
    ) {
        return ResponseEntity.ok(placeService.updatePlace(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaceDto.Response> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(placeService.deletePlace(id));
    }

}
