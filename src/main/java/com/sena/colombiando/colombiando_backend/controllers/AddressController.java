package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.AddressDto;
import com.sena.colombiando.colombiando_backend.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

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

    @PostMapping
    public ResponseEntity<AddressDto.Response> create(
            @Valid @RequestBody AddressDto.Create request
    ) {
        AddressDto.Response created = addressService.createAddress(request);
        return ResponseEntity.created(URI.create("/api/addresss/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto.Response> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.getAddress(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AddressDto.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody AddressDto.Update request
    ) {
        return ResponseEntity.ok(addressService.updateAddress(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressDto.Response> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.deleteAddress(id));
    }

}
