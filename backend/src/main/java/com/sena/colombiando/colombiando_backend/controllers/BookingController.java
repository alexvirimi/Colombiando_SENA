package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.BookingDto;
import com.sena.colombiando.colombiando_backend.entities.BookingStatusEnum;
import com.sena.colombiando.colombiando_backend.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Bookings", description = "Gestión de reservas")
public class BookingController {

    private final BookingService bookingService;
/** Inicializa la instancia.
 * @param bookingService parametro de entrada.
 */
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

/** Consulta all bookings.
 * @param userId parametro de entrada.
 * @param scheduleInstanceId parametro de entrada.
 * @param status parametro de entrada.
 * @param fromDate parametro de entrada.
 * @param toDate parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Listar reservas", description = "Consulta reservas con filtros opcionales.")
    @ApiResponse(responseCode = "200", description = "Reservas consultadas correctamente.")
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

/** Crea .
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Crear reserva")
    @ApiResponse(responseCode = "201", description = "Reserva creada correctamente.")
    @PostMapping
    public ResponseEntity<BookingDto.Response> create(
            @Valid @RequestBody BookingDto.Create request
    ) {
        BookingDto.Response created = bookingService.createBooking(request);
        return ResponseEntity.created(URI.create("/api/bookings" + created.id())).body(created);
    }

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta booking.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Consultar reserva por ID")
    @ApiResponse(responseCode = "200", description = "Reserva consultada correctamente.")
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto.Response> getBooking(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

/** Ejecuta la operacion patch mapping.
 * @param id parametro de entrada.
 */
/** Actualiza .
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Actualizar reserva")
    @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente.")
    @PatchMapping("/{id}")
    public ResponseEntity<BookingDto.Response> update(
            @PathVariable UUID id,
            @RequestBody BookingDto.Update request
    ) {
        return ResponseEntity.ok(bookingService.updateBooking(id, request));
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina .
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Eliminar reserva")
    @ApiResponse(responseCode = "200", description = "Reserva eliminada correctamente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<BookingDto.Response> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(bookingService.deleteBooking(id));
    }

}
