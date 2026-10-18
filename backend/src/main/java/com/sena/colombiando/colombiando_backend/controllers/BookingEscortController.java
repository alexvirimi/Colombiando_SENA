package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.BookingEscortDto;
import com.sena.colombiando.colombiando_backend.services.BookingEscortService;
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
@RequestMapping("/booking-escorts")
@Tag(name = "Booking Escorts", description = "Gestión de acompañantes de reservas")
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
    @Operation(summary = "Listar acompañantes de reservas")
    @ApiResponse(responseCode = "200", description = "Acompañantes consultados correctamente.")
    @GetMapping
    public ResponseEntity<List<BookingEscortDto.Response>> getBookingEscorts() {
        return ResponseEntity.ok(bookingEscortService.getAllBookingEscorts());
    }

/** Crea .
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Crear acompañante de reserva")
    @ApiResponse(responseCode = "201", description = "Acompañante creado correctamente.")
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
    @Operation(summary = "Consultar acompañante por ID")
    @ApiResponse(responseCode = "200", description = "Acompañante consultado correctamente.")
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
    @Operation(summary = "Listar acompañantes por reserva")
    @ApiResponse(responseCode = "200", description = "Acompañantes consultados correctamente.")
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
    @Operation(summary = "Contar acompañantes de una reserva")
    @ApiResponse(responseCode = "200", description = "Cantidad consultada correctamente.")
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
    @Operation(summary = "Actualizar acompañante")
    @ApiResponse(responseCode = "200", description = "Acompañante actualizado correctamente.")
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
    @Operation(summary = "Eliminar acompañante")
    @ApiResponse(responseCode = "200", description = "Acompañante eliminado correctamente.")
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
    @Operation(summary = "Eliminar acompañantes por reserva")
    @ApiResponse(responseCode = "200", description = "Acompañantes eliminados correctamente.")
    @DeleteMapping("/by-booking/{id}")
    public ResponseEntity<List<BookingEscortDto.Response>> deleteByBooking(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(bookingEscortService.deleteEscortsByBookingId(id));
    }
}
