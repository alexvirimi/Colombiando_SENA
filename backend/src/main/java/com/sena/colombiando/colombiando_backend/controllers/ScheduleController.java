package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.ScheduleDto;
import com.sena.colombiando.colombiando_backend.entities.ScheduleStatusEnum;
import com.sena.colombiando.colombiando_backend.services.ScheduleService;
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
@RequestMapping("/schedules")
@Tag(name = "Schedules", description = "Gestión de horarios")
public class ScheduleController {

    private final ScheduleService scheduleService;
/** Inicializa la instancia.
 * @param scheduleService parametro de entrada.
 */
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

/** Consulta all schedules.
 * @param guideId parametro de entrada.
 * @param placeId parametro de entrada.
 * @param status parametro de entrada.
 * @param startDate parametro de entrada.
 * @param endDate parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Listar horarios", description = "Consulta horarios con filtros opcionales.")
    @ApiResponse(responseCode = "200", description = "Horarios consultados correctamente.")
    @GetMapping
    public ResponseEntity<List<ScheduleDto.Response>> getAllSchedules(
            @RequestParam(required = false) UUID guideId,
            @RequestParam(required = false) UUID placeId,
            @RequestParam(required = false) ScheduleStatusEnum status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(scheduleService.searchSchedules(guideId, placeId, status, startDate, endDate));
    }

/** Crea schedule.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Crear horario")
    @ApiResponse(responseCode = "201", description = "Horario creado correctamente.")
    @PostMapping
    public ResponseEntity<ScheduleDto.Response> createSchedule(
            @Valid @RequestBody ScheduleDto.Create request
    ) {
        ScheduleDto.Response created =  scheduleService.createSchedule(request);
        return ResponseEntity.created(URI.create("/api/schedules/" + created.id())).body(created);
    }

    @Operation(summary = "Consultar horario por ID")
    @ApiResponse(responseCode = "200", description = "Horario consultado correctamente.")
    @ApiResponse(responseCode = "404", description = "Horario no encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto.Response> getSchedule(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(scheduleService.getSchedule(id));
    }

    /** Ejecuta la operacion patch mapping.
     * @param id parametro de entrada.
     */
    /** Actualiza .
     * @param id parametro de entrada.
     * @param request parametro de entrada.
     * @return resultado de la operacion.
     */
    @Operation(summary = "Actualizar horario")
    @ApiResponse(responseCode = "200", description = "Horario actualizado correctamente.")
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleDto.Response> update(
            @PathVariable UUID id,
            @RequestBody ScheduleDto.Update request
    ) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, request));
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina .
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Eliminar horario")
    @ApiResponse(responseCode = "200", description = "Horario eliminado correctamente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleDto.Response> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(id));
    }

}
