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
    public ScheduleInstanceController(ScheduleInstanceService scheduleInstanceService) {
        this.scheduleInstanceService = scheduleInstanceService;
    }

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

    @PostMapping
    public ResponseEntity<ScheduleInstanceDto.Response> create(
            @RequestBody ScheduleInstanceDto.Create request
    ) {
        ScheduleInstanceDto.Response created = scheduleInstanceService.createScheduleInstace(request);
        return ResponseEntity.created(URI.create("/api/schedule-instances/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleInstanceDto.Response> getScheduleInstance(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(scheduleInstanceService.getScheduleInstance(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleInstanceDto.Response> update(
            @PathVariable UUID id,
            @RequestBody ScheduleInstanceDto.Update request
    ) {
        return ResponseEntity.ok(
                scheduleInstanceService.updateScheduleInstance(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleInstanceDto.Response> deleteScheduleInstance(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(scheduleInstanceService.deleteScheduleInstance(id));
    }

}
