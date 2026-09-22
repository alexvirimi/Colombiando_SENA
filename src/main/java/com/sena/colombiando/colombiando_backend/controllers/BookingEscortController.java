package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.BookingEscortDto;
import com.sena.colombiando.colombiando_backend.services.BookingEscortService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking-escorts")
public class BookingEscortController {

    private final BookingEscortService bookingEscortService;
    public BookingEscortController (
            BookingEscortService bookingEscortService
    ) {
        this.bookingEscortService = bookingEscortService;
    }

    @GetMapping
    public ResponseEntity<List<BookingEscortDto.Response>> getBookingEscorts() {
        return ResponseEntity.ok(bookingEscortService.getAllBookingEscorts());
    }

    @PostMapping
    public ResponseEntity<BookingEscortDto.Response> create(
            @Valid @RequestBody BookingEscortDto.Create request
    ) {
        BookingEscortDto.Response created = bookingEscortService.createBookingEscort(
                request
        );
        return ResponseEntity.created(
                URI.create("/api/booking-escorts/" + created.id())
        ).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingEscortDto.Response> getById(@PathVariable UUID id){
        return ResponseEntity.ok(bookingEscortService.getBookingEscort(id));
    }

    @GetMapping("/by-booking/{bookingId}")
    public ResponseEntity<List<BookingEscortDto.Response>> getByBookingId(@PathVariable UUID bookingId){
        return ResponseEntity.ok(bookingEscortService.getAllBookingsByBookingId(bookingId));
    }

    @GetMapping("/count-escorts/{bookingId}")
    public ResponseEntity<Integer> getEscortCountByBooking(
            @PathVariable UUID bookingId
    ) {
        return ResponseEntity.ok(bookingEscortService.countEscortsByBookingId(bookingId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingEscortDto.Response> update(
            @PathVariable UUID id,
            @RequestBody BookingEscortDto.Update request
    ) {
        return ResponseEntity.ok(bookingEscortService.updateBooking(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingEscortDto.Response> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(bookingEscortService.deleteBooking(id));
    }

    @DeleteMapping("/by-booking/{id}")
    public ResponseEntity<List<BookingEscortDto.Response>> deleteByBooking(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(bookingEscortService.deleteEscortsByBookingId(id));
    }
}
