package com.sena.colombiando.colombiando_backend.services;

import com.sena.colombiando.colombiando_backend.dto.UserDto;
import com.sena.colombiando.colombiando_backend.entities.UserEntity;
import com.sena.colombiando.colombiando_backend.entities.UserStatusEnum;
import com.sena.colombiando.colombiando_backend.mappers.UserMapper;
import com.sena.colombiando.colombiando_backend.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto.Response createUser(UserDto.Create request) {
        var dataRequest = request.data();

        if (userRepository.existsByEmail(dataRequest.email())) {
            throw new EntityExistsException("Ya existe un usuario con ese correo.");
        }

        UserEntity user = userMapper.toEntity(request);
        String hashedPassword = passwordEncoder.encode(request.password());
        user.setPassword(hashedPassword);

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto.Response updateUser(UUID id, UserDto.Update request) {
        var dataRequest = request.data();

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));

        Optional.ofNullable(dataRequest.name())
                .ifPresent(user::setName);
        Optional.ofNullable(dataRequest.lastName())
                .ifPresent(user::setLastName);
        Optional.ofNullable(dataRequest.phone())
                .ifPresent(user::setPhone);
        Optional.ofNullable(dataRequest.email())
                .ifPresent(user::setEmail);
        Optional.ofNullable(request.status())
                .ifPresent(user::setStatus);

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto.Response changePassword(UUID id, UserDto.ChangePassword request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
        }

        String encodedPassword = passwordEncoder.encode(request.newPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto.Response deleteUser(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));

        String originalEmail = user.getEmail();
        String usernamePrefix = originalEmail.contains("@")
                ? originalEmail.split("@")[0]
                : "user";
        String deletedEmail = String.format("%s+%s@deleted.local", usernamePrefix, user.getId());

        user.setStatus(UserStatusEnum.DELETED);
        user.setEmail(deletedEmail);
        user.setNameLastNamePhonePasswordToNull();

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto.Response getUser(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto.Response getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return userMapper.toDto(user);
    }

    @Transactional
    public List<UserDto.Response> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDto.Response> responses = new ArrayList<>();

        for (UserEntity user : users) {
            responses.add(userMapper.toDto(user));
        }

        return responses;
    }
}
