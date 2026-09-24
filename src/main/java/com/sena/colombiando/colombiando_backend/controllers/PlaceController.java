package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.PlaceDto;
import com.sena.colombiando.colombiando_backend.services.PlaceService;
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
@RequestMapping("/places")
@Tag(name = "Places", description = "Gestión de lugares turísticos")
public class PlaceController {

    private final PlaceService placeService;
/** Inicializa la instancia.
 * @param placeService parametro de entrada.
 */
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

/** Consulta find all.
 * @param containing parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Listar lugares", description = "Consulta lugares y permite filtrar por nombre.")
    @ApiResponse(responseCode = "200", description = "Lugares consultados correctamente.")
    @GetMapping
    public ResponseEntity<List<PlaceDto.Response>> findAll(
            @RequestParam(required = false) String containing
    ) {
        if (containing != null) {
            return ResponseEntity.ok(placeService.getPlaceByNameContaining(containing));
        }
        return ResponseEntity.ok(placeService.getAllPlaces());
    }

/** Crea .
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Crear lugar")
    @ApiResponse(responseCode = "201", description = "Lugar creado correctamente.")
    @PostMapping
    public ResponseEntity<PlaceDto.Response> create(
            @Valid @RequestBody PlaceDto.Create request
    ) {
        PlaceDto.Response created = placeService.createPlace(request);
        return ResponseEntity.created(URI.create("/api/places/" + created.id())).body(created);
    }

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta find by id.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Consultar lugar por ID")
    @ApiResponse(responseCode = "200", description = "Lugar consultado correctamente.")
    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto.Response> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(placeService.getPlace(id));
    }

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta find by address.
 * @param addressId parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Consultar lugar por dirección")
    @ApiResponse(responseCode = "200", description = "Lugar consultado correctamente.")
    @GetMapping("/by-address/{addressId}")
    public ResponseEntity<PlaceDto.Response> findByAddress(@PathVariable UUID addressId) {
        return ResponseEntity.ok(placeService.getPlaceByAddressId(addressId));
    }

/** Ejecuta la operacion patch mapping.
 * @param id parametro de entrada.
 */
/** Actualiza .
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Actualizar lugar")
    @ApiResponse(responseCode = "200", description = "Lugar actualizado correctamente.")
    @PatchMapping("/{id}")
    public ResponseEntity<PlaceDto.Response> update(
            @PathVariable UUID id,
            @RequestBody PlaceDto.Update request
    ) {
        return ResponseEntity.ok(placeService.updatePlace(id, request));
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina .
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Eliminar lugar")
    @ApiResponse(responseCode = "200", description = "Lugar eliminado correctamente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<PlaceDto.Response> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(placeService.deletePlace(id));
    }

}
