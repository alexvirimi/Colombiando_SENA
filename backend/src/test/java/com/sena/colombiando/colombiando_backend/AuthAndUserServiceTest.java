package com.sena.colombiando.colombiando_backend;

import com.sena.colombiando.colombiando_backend.dto.AuthDto;
import com.sena.colombiando.colombiando_backend.dto.UserDto;
import com.sena.colombiando.colombiando_backend.entities.UserEntity;
import com.sena.colombiando.colombiando_backend.entities.UserStatusEnum;
import com.sena.colombiando.colombiando_backend.exceptions.InvalidCredentialsException;
import com.sena.colombiando.colombiando_backend.mappers.UserMapper;
import com.sena.colombiando.colombiando_backend.repositories.UserRepository;
import com.sena.colombiando.colombiando_backend.services.AuthService;
import com.sena.colombiando.colombiando_backend.services.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthAndUserServiceTest {

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;

    private AuthService authService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userRepository, passwordEncoder);
        userService = new UserService(userRepository, new UserMapper(), passwordEncoder);
    }

    @Test
    void cpAut01_loginWithActiveUser() {
        UserEntity user = user("user@test.com", UserStatusEnum.ACTIVE);
        user.setPassword("hash");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);

        AuthDto.AuthResponse response = authService.login(new AuthDto.LoginRequest(user.getEmail(), "secret"));

        assertEquals("Inicio de sesión exitoso.", response.message());
    }

    @Test
    void cpAut02_loginWithWrongPassword() {
        UserEntity user = user("user@test.com", UserStatusEnum.ACTIVE);
        user.setPassword("hash");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(passwordEncoder.matches("wrong", "hash")).thenReturn(false);

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class,
                () -> authService.login(new AuthDto.LoginRequest(user.getEmail(), "wrong")));

        assertEquals("Correo o contraseña incorrectos.", exception.getMessage());
    }

    @Test
    void cpAut03_loginWithUnknownEmailUsesGenericError() {
        when(userRepository.findByEmail("unknown@test.com")).thenReturn(null);

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class,
                () -> authService.login(new AuthDto.LoginRequest("unknown@test.com", "secret")));

        assertEquals("Correo o contraseña incorrectos.", exception.getMessage());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void cpAut04_loginWithInactiveUserIsDenied() {
        UserEntity user = user("user@test.com", UserStatusEnum.INACTIVE);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        assertThrows(InvalidCredentialsException.class,
                () -> authService.login(new AuthDto.LoginRequest(user.getEmail(), "secret")));
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void cpAut05_registerHashesPassword() {
        when(userRepository.existsByEmail("new@test.com")).thenReturn(false);
        when(passwordEncoder.encode("secret")).thenReturn("bcrypt-hash");

        authService.register(new AuthDto.RegisterRequest("New", "User", "new@test.com", "300", "secret"));

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        assertEquals("bcrypt-hash", captor.getValue().getPassword());
        assertNotEquals("secret", captor.getValue().getPassword());
    }

    @Test
    void cpAut06_registerRejectsDuplicateEmail() {
        when(userRepository.existsByEmail("used@test.com")).thenReturn(true);

        assertThrows(EntityExistsException.class,
                () -> authService.register(new AuthDto.RegisterRequest("Used", "User", "used@test.com", null, "secret")));
        verify(userRepository, never()).save(any());
    }

    @Test
    void cpUsr01_listsUsers() {
        UserEntity first = user("one@test.com", UserStatusEnum.ACTIVE);
        UserEntity second = user("two@test.com", UserStatusEnum.ACTIVE);
        when(userRepository.findAll()).thenReturn(List.of(first, second));

        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void cpUsr02_findsUserByIdAndEmail() {
        UUID id = UUID.randomUUID();
        UserEntity user = user("user@test.com", UserStatusEnum.ACTIVE);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        assertNotNull(userService.getUser(id));
        assertNotNull(userService.getUserByEmail(user.getEmail()));
    }

    @Test
    void cpUsr03_rejectsUnknownUser() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUser(id));
    }

    @Test
    void cpUsr04_updatesUserData() {
        UUID id = UUID.randomUUID();
        UserEntity user = user("old@test.com", UserStatusEnum.ACTIVE);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDto.Update request = new UserDto.Update(
                new UserDto.Base("New", "Name", "new@test.com", "311"), null);
        userService.updateUser(id, request);

        assertEquals("New", user.getName());
        assertEquals("new@test.com", user.getEmail());
        assertEquals("311", user.getPhone());
        verify(userRepository).save(user);
    }

    @Test
    void cpUsr05_rejectsEmailUsedByAnotherUser() {
        UUID id = UUID.randomUUID();
        UserEntity user = user("old@test.com", UserStatusEnum.ACTIVE);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmailAndIdNot("used@test.com", id)).thenReturn(true);

        UserDto.Update request = new UserDto.Update(
                new UserDto.Base("New", "Name", "used@test.com", "311"), null);

        assertThrows(EntityExistsException.class, () -> userService.updateUser(id, request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void cpUsr06_changesPasswordWithCurrentPassword() {
        UUID id = UUID.randomUUID();
        UserEntity user = user("user@test.com", UserStatusEnum.ACTIVE);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("old"));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserService service = new UserService(userRepository, new UserMapper(), encoder);

        service.changePassword(id, new UserDto.ChangePassword("old", "new"));

        assertTrue(encoder.matches("new", user.getPassword()));
        assertFalse(encoder.matches("old", user.getPassword()));
    }

    @Test
    void cpUsr07_rejectsWrongCurrentPassword() {
        UUID id = UUID.randomUUID();
        UserEntity user = user("user@test.com", UserStatusEnum.ACTIVE);
        user.setPassword("old-hash");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "old-hash")).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> userService.changePassword(id, new UserDto.ChangePassword("wrong", "new")));
        assertEquals("old-hash", user.getPassword());
        verify(userRepository, never()).save(any());
    }

    private UserEntity user(String email, UserStatusEnum status) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setName("User");
        user.setLastName("Test");
        user.setStatus(status);
        return user;
    }
}
