package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.UserDto;
import com.sena.colombiando.colombiando_backend.services.UserService;
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
@RequestMapping("/users")
@Tag(name = "Users", description = "Gestión de usuarios")
public class UserController {

    private UserService userService;
/** Inicializa la instancia.
 * @param userService parametro de entrada.
 */
    public UserController(UserService userService) {
        this.userService = userService;
    }

/** Consulta all.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Listar usuarios")
    @ApiResponse(responseCode = "200", description = "Usuarios consultados correctamente.")
    @GetMapping
    public ResponseEntity<List<UserDto.Response>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

/** Crea .
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Crear usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente.")
    @PostMapping
    public ResponseEntity<UserDto.Response> create(
            @Valid @RequestBody UserDto.Create request
    ) {
        UserDto.Response created = userService.createUser(request);
        return ResponseEntity.created(URI.create("/api/users/" + created.id())).body(created);
    }

/** Ejecuta la operacion get mapping.
 * @param id parametro de entrada.
 */
/** Consulta by id.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Consultar usuario por ID")
    @ApiResponse(responseCode = "200", description = "Usuario consultado correctamente.")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

/** Ejecuta la operacion get mapping.
 * @param email parametro de entrada.
 */
/** Consulta by email.
 * @param email parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Consultar usuario por correo")
    @ApiResponse(responseCode = "200", description = "Usuario consultado correctamente.")
    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDto.Response> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

/** Ejecuta la operacion patch mapping.
 * @param id parametro de entrada.
 */
/** Actualiza .
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Actualizar usuario")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente.")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDto.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody UserDto.Update request
    ) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

/** Ejecuta la operacion patch mapping.
 * @param id parametro de entrada.
 */
/** Cambia password.
 * @param id parametro de entrada.
 * @param request parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Cambiar contraseña")
    @ApiResponse(responseCode = "200", description = "Contraseña actualizada correctamente.")
    @PatchMapping("/change-password/{id}")
    public ResponseEntity<UserDto.Response> changePassword(
            @PathVariable UUID id,
            @Valid @RequestBody UserDto.ChangePassword request
    ) {
        return ResponseEntity.ok(userService.changePassword(id, request));
    }

/** Ejecuta la operacion delete mapping.
 * @param id parametro de entrada.
 */
/** Elimina by id.
 * @param id parametro de entrada.
 * @return resultado de la operacion.
 */
    @Operation(summary = "Eliminar usuario")
    @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto.Response> deleteById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
