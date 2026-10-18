package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.AddressDto;
import com.sena.colombiando.colombiando_backend.services.AddressService;
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
@RequestMapping("/addresses")
@Tag(name = "Addresses", description = "Gestión de direcciones")
public class AddressController {

    private final AddressService addressService;
/** Inicializa la instancia.
 * @param addressService parametro de entrada.
 */
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

/** Consulta all addresses.
 * @param countryCode parametro de entrada.
 * @param municipality parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Listar direcciones", description = "Consulta todas las direcciones o filtra por país o municipio.")
    @ApiResponse(responseCode = "200", description = "Direcciones consultadas correctamente.")
    @GetMapping
    public ResponseEntity<List<AddressDto.Response>> getAllAddresses(
            @RequestParam(required = false) String countryCode,
            @RequestParam(required = false) String municipality
    ){
        if (countryCode != null) {
            return ResponseEntity.ok(addressService.getAllAddressesByCountryCode(countryCode));
        }
        if (municipality != null) {
            return ResponseEntity.ok(addressService.getAllAddressesByMunicipality(municipality));
        }
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

/** Crea .
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Crear dirección")
    @ApiResponse(responseCode = "201", description = "Dirección creada correctamente.")
    @PostMapping
    public ResponseEntity<AddressDto.Response> create(
            @Valid @RequestBody AddressDto.Create request
    ) {
        AddressDto.Response created = addressService.createAddress(request);
        return ResponseEntity.created(URI.create("/api/addresss/" + created.id())).body(created);
    }

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta by id.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Consultar dirección por ID")
    @ApiResponse(responseCode = "200", description = "Dirección consultada correctamente.")
    @GetMapping("/{id}")
    public ResponseEntity<AddressDto.Response> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.getAddress(id));
    }

/** Ejecuta la operacion patch mapping.
 * @param id parametro de entrada.
 */
/** Actualiza .
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Actualizar dirección")
    @ApiResponse(responseCode = "200", description = "Dirección actualizada correctamente.")
    @PatchMapping("/{id}")
    public ResponseEntity<AddressDto.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody AddressDto.Update request
    ) {
        return ResponseEntity.ok(addressService.updateAddress(id, request));
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina .
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Eliminar dirección")
    @ApiResponse(responseCode = "200", description = "Dirección eliminada correctamente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<AddressDto.Response> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.deleteAddress(id));
    }

}
