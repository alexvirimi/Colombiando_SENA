package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.PaymentDto;
import com.sena.colombiando.colombiando_backend.entities.PaymentStatusEnum;
import com.sena.colombiando.colombiando_backend.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDto.Response>> getPayments(
            @RequestParam(required = false) UUID bookingId,
            @RequestParam(required = false) PaymentStatusEnum status
    ) {
        return ResponseEntity.ok(paymentService.searchPayments(bookingId, status));
    }

    @PostMapping
    public ResponseEntity<PaymentDto.Response> createPayment(
            @RequestBody PaymentDto.Create request
    ) {
        PaymentDto.Response created = paymentService.createPayment(request);
        return ResponseEntity.created(URI.create("/api/payments/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto.Response> getPayment(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PaymentDto.Response> update(
            @PathVariable UUID id,
            @RequestBody PaymentDto.Update request
    ) {
        return ResponseEntity.ok(paymentService.updatePayment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto.Response> deletePayment(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(paymentService.deletePayment(id));
    }

}
