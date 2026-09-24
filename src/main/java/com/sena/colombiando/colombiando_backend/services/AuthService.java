package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.AuthDto;
import com.sena.colombiando.colombiando_backend.entities.UserEntity;
import com.sena.colombiando.colombiando_backend.entities.UserStatusEnum;
import com.sena.colombiando.colombiando_backend.exceptions.InvalidCredentialsException;
import com.sena.colombiando.colombiando_backend.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Inicializa la instancia.
     * @param userRepository repositorio de usuarios, para buscar por email y guardar.
     * @param passwordEncoder encoder BCrypt inyectado desde SecurityConfig.
     */
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario.
     * Reglas:
     *  1. El email debe ser único (si ya existe, 409 Conflict vía EntityExistsException).
     *  2. La contraseña NUNCA se guarda en texto plano: se hashea con BCrypt
     *     antes de persistir (passwordEncoder.encode).
     *
     * @param request datos de registro (nombre, apellido, email, teléfono, password).
     * @return AuthResponse con los datos públicos del usuario recién creado.
     */
    @Transactional
    public AuthDto.AuthResponse register(AuthDto.RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EntityExistsException("Ya existe un usuario con ese correo.");
        }

        UserEntity user = new UserEntity();
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        // Nunca se guarda request.password() directo: se hashea con BCrypt.
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        return new AuthDto.AuthResponse(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getStatus(),
                "Usuario registrado correctamente."
        );
    }

    /**
     * Valida credenciales de login.
     * Flujo:
     *  1. Busca el usuario por email.
     *  2. Si no existe -> credenciales inválidas (mensaje genérico, no decimos
     *     "el usuario no existe" para no filtrar información).
     *  3. Si el usuario está eliminado/inactivo/suspendido -> credenciales inválidas
     *     (no dejamos iniciar sesión aunque el password sea correcto).
     *  4. Compara el password recibido en texto plano contra el hash BCrypt
     *     guardado, usando passwordEncoder.matches (NUNCA comparar con == o equals).
     *  5. Si todo es correcto, devuelve los datos públicos del usuario.
     *
     * @param request email + password en texto plano.
     * @return AuthResponse si las credenciales son válidas.
     * @throws InvalidCredentialsException si el usuario no existe, no está activo,
     *         o la contraseña no coincide.
     */
    @Transactional
    public AuthDto.AuthResponse login(AuthDto.LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email());

        if (user == null) {
            throw new InvalidCredentialsException("Correo o contraseña incorrectos.");
        }

        if (user.getStatus() != UserStatusEnum.ACTIVE) {
            throw new InvalidCredentialsException("Correo o contraseña incorrectos.");
        }

        boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPassword());
        if (!passwordMatches) {
            throw new InvalidCredentialsException("Correo o contraseña incorrectos.");
        }

        return new AuthDto.AuthResponse(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getStatus(),
                "Inicio de sesión exitoso."
        );
    }
}
