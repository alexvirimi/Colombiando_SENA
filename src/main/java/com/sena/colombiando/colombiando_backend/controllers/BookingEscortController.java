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
/** Inicializa la instancia.
 * @param bookingEscortService parametro de entrada.
 */
    public BookingEscortController (
            BookingEscortService bookingEscortService
    ) {
        this.bookingEscortService = bookingEscortService;
    }

/** Consulta booking escorts.
 * @return resultado de la operacion.
 */
    @GetMapping
    public ResponseEntity<List<BookingEscortDto.Response>> getBookingEscorts() {
        return ResponseEntity.ok(bookingEscortService.getAllBookingEscorts());
    }

/** Crea .
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
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

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta by id.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @GetMapping("/{id}")
    public ResponseEntity<BookingEscortDto.Response> getById(@PathVariable UUID id){
        return ResponseEntity.ok(bookingEscortService.getBookingEscort(id));
    }

/** Ejecuta la operacion get mapping.
 * @param bookingId parametro de entrada.
 */
/** Consulta by booking id.
 * @param bookingId parametro de entrada.
 * @return resultado de la operacion.
 */
    @GetMapping("/by-booking/{bookingId}")
    public ResponseEntity<List<BookingEscortDto.Response>> getByBookingId(@PathVariable UUID bookingId){
        return ResponseEntity.ok(bookingEscortService.getAllBookingsByBookingId(bookingId));
    }

/** Ejecuta la operacion get mapping.
 * @param bookingId parametro de entrada.
 */
/** Consulta escort count by booking.
 * @param bookingId parametro de entrada.
 * @return resultado de la operacion.
 */
    @GetMapping("/count-escorts/{bookingId}")
    public ResponseEntity<Integer> getEscortCountByBooking(
            @PathVariable UUID bookingId
    ) {
        return ResponseEntity.ok(bookingEscortService.countEscortsByBookingId(bookingId));
    }

/** Ejecuta la operacion patch mapping.
 * @param id parametro de entrada.
 */
/** Actualiza .
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @PatchMapping("/{id}")
    public ResponseEntity<BookingEscortDto.Response> update(
            @PathVariable UUID id,
            @RequestBody BookingEscortDto.Update request
    ) {
        return ResponseEntity.ok(bookingEscortService.updateBooking(id, request));
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina .
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @DeleteMapping("/{id}")
    public ResponseEntity<BookingEscortDto.Response> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(bookingEscortService.deleteBooking(id));
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina by booking.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @DeleteMapping("/by-booking/{id}")
    public ResponseEntity<List<BookingEscortDto.Response>> deleteByBooking(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(bookingEscortService.deleteEscortsByBookingId(id));
    }
}
