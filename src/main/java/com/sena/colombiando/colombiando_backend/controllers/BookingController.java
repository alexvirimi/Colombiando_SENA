package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.BookingDto;
import com.sena.colombiando.colombiando_backend.entities.BookingStatusEnum;
import com.sena.colombiando.colombiando_backend.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDto.Response>> getAllBookings(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID scheduleInstanceId,
            @RequestParam(required = false) BookingStatusEnum status,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate
    ) {
        return ResponseEntity.ok(bookingService.searchBookings(userId, scheduleInstanceId, status, fromDate, toDate));
    }

    @PostMapping
    public ResponseEntity<BookingDto.Response> create(
            @Valid @RequestBody BookingDto.Create request
    ) {
        BookingDto.Response created = bookingService.createBooking(request);
        return ResponseEntity.created(URI.create("/api/bookings" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto.Response> getBooking(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingDto.Response> update(
            @PathVariable UUID id,
            @RequestBody BookingDto.Update request
    ) {
        return ResponseEntity.ok(bookingService.updateBooking(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingDto.Response> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(bookingService.deleteBooking(id));
    }

}
