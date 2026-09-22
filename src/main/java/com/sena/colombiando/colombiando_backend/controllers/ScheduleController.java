package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.ScheduleDto;
import com.sena.colombiando.colombiando_backend.entities.ScheduleStatusEnum;
import com.sena.colombiando.colombiando_backend.services.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedules")
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
    @PostMapping
    public ResponseEntity<ScheduleDto.Response> createSchedule(
            @Valid @RequestBody ScheduleDto.Create request
    ) {
        ScheduleDto.Response created =  scheduleService.createSchedule(request);
        return ResponseEntity.created(URI.create("/api/schedules/" + created.id())).body(created);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleDto.Response> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(id));
    }

}
