package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.AuthDto;
import com.sena.colombiando.colombiando_backend.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Registro e inicio de sesión de usuarios")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /** Inicializa la instancia.
     * @param authService servicio con la lógica de registro y login.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * @param request nombre, apellido, email, teléfono (opcional) y password.
     * @return 201 Created con los datos públicos del usuario creado.
     */
    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Crea un usuario nuevo. El password se recibe en texto plano " +
                    "y se guarda hasheado con BCrypt. El email debe ser único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (campos vacíos, email mal formado)."),
            @ApiResponse(responseCode = "409", description = "Ya existe un usuario con ese correo.")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthDto.AuthResponse> register(
            @Valid @RequestBody AuthDto.RegisterRequest request
    ) {
        AuthDto.AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Inicia sesión: verifica que el usuario exista, esté activo y que el
     * password recibido coincida con el hash BCrypt almacenado.
     * @param request email y password en texto plano.
     * @return 200 OK con los datos del usuario si las credenciales son correctas.
     */
    @Operation(
            summary = "Iniciar sesión",
            description = "Valida email + password contra la base de datos. " +
                    "El password recibido se compara contra el hash BCrypt " +
                    "guardado usando PasswordEncoder.matches()."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso, credenciales correctas."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (email o password vacíos)."),
            @ApiResponse(responseCode = "401", description = "Correo o contraseña incorrectos, o usuario no activo.")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthDto.AuthResponse> login(
            @Valid @RequestBody AuthDto.LoginRequest request
    ) {
        AuthDto.AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
