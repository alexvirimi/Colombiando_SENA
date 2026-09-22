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
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

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

    @PostMapping
    public ResponseEntity<ScheduleDto.Response> createSchedule(
            @Valid @RequestBody ScheduleDto.Create request
    ) {
        ScheduleDto.Response created =  scheduleService.createSchedule(request);
        return ResponseEntity.created(URI.create("/api/schedules/" + created.id())).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleDto.Response> update(
            @PathVariable UUID id,
            @RequestBody ScheduleDto.Update request
    ) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleDto.Response> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(id));
    }

}
