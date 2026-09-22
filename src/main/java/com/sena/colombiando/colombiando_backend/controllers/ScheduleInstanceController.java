package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.ScheduleInstanceDto;
import com.sena.colombiando.colombiando_backend.entities.ScheduleInstanceStateEnum;
import com.sena.colombiando.colombiando_backend.services.ScheduleInstanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule-instances")
public class ScheduleInstanceController {

    private final ScheduleInstanceService scheduleInstanceService;
/** Inicializa la instancia.
 * @param scheduleInstanceService parametro de entrada.
 */
    public ScheduleInstanceController(ScheduleInstanceService scheduleInstanceService) {
        this.scheduleInstanceService = scheduleInstanceService;
    }

/** Consulta all schedule instances.
 * @param scheduleID parametro de entrada.
 * @param placeId parametro de entrada.
 * @param state parametro de entrada.
 * @param fromDate parametro de entrada.
 * @param toDate parametro de entrada.
 * @param minCapacity parametro de entrada.
 * @return resultado de la operacion.
 */
    @GetMapping
    public ResponseEntity<List<ScheduleInstanceDto.Response>> getAllScheduleInstances(
            @RequestParam(required = false) UUID scheduleID,
            @RequestParam(required = false) UUID placeId,
            @RequestParam(required = false) ScheduleInstanceStateEnum state,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) Integer minCapacity
    ) {
        return ResponseEntity.ok(scheduleInstanceService
                .searchScheduleInstances(
                        scheduleID, placeId, state,
                        fromDate, toDate, minCapacity
                )
        );
    }

/** Crea .
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @PostMapping
    public ResponseEntity<ScheduleInstanceDto.Response> create(
            @RequestBody ScheduleInstanceDto.Create request
    ) {
        ScheduleInstanceDto.Response created = scheduleInstanceService.createScheduleInstace(request);
        return ResponseEntity.created(URI.create("/api/schedule-instances/" + created.id())).body(created);
    }

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta schedule instance.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleInstanceDto.Response> getScheduleInstance(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(scheduleInstanceService.getScheduleInstance(id));
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
    public ResponseEntity<ScheduleInstanceDto.Response> update(
            @PathVariable UUID id,
            @RequestBody ScheduleInstanceDto.Update request
    ) {
        return ResponseEntity.ok(
                scheduleInstanceService.updateScheduleInstance(id, request)
        );
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina schedule instance.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleInstanceDto.Response> deleteScheduleInstance(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(scheduleInstanceService.deleteScheduleInstance(id));
    }

}
