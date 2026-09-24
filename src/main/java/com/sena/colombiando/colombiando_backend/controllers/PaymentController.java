package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.PaymentDto;
import com.sena.colombiando.colombiando_backend.entities.PaymentStatusEnum;
import com.sena.colombiando.colombiando_backend.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payments", description = "Gestión de pagos")
public class PaymentController {

    private final PaymentService paymentService;
/** Inicializa la instancia.
 * @param paymentService parametro de entrada.
 */
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

/** Consulta payments.
 * @param bookingId parametro de entrada.
 * @param status parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Listar pagos", description = "Consulta pagos con filtros opcionales.")
    @ApiResponse(responseCode = "200", description = "Pagos consultados correctamente.")
    @GetMapping
    public ResponseEntity<List<PaymentDto.Response>> getPayments(
            @RequestParam(required = false) UUID bookingId,
            @RequestParam(required = false) PaymentStatusEnum status
    ) {
        return ResponseEntity.ok(paymentService.searchPayments(bookingId, status));
    }

/** Crea payment.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Crear pago")
    @ApiResponse(responseCode = "201", description = "Pago creado correctamente.")
    @PostMapping
    public ResponseEntity<PaymentDto.Response> createPayment(
            @RequestBody PaymentDto.Create request
    ) {
        PaymentDto.Response created = paymentService.createPayment(request);
        return ResponseEntity.created(URI.create("/api/payments/" + created.id())).body(created);
    }

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta payment.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Consultar pago por ID")
    @ApiResponse(responseCode = "200", description = "Pago consultado correctamente.")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto.Response> getPayment(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

/** Ejecuta la operacion patch mapping.
 * @param id parametro de entrada.
 */
/** Actualiza .
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Actualizar pago")
    @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente.")
    @PatchMapping("/{id}")
    public ResponseEntity<PaymentDto.Response> update(
            @PathVariable UUID id,
            @RequestBody PaymentDto.Update request
    ) {
        return ResponseEntity.ok(paymentService.updatePayment(id, request));
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina payment.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Eliminar pago")
    @ApiResponse(responseCode = "200", description = "Pago eliminado correctamente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto.Response> deletePayment(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(paymentService.deletePayment(id));
    }

}
