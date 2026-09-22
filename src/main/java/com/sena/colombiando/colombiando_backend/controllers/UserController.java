package com.sena.colombiando.colombiando_backend.controllers;

import com.sena.colombiando.colombiando_backend.dto.UserDto;
import com.sena.colombiando.colombiando_backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto.Response>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserDto.Response> create(
            @Valid @RequestBody UserDto.Create request
    ) {
        UserDto.Response created = userService.createUser(request);
        return ResponseEntity.created(URI.create("/api/users/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDto.Response> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody UserDto.Update request
    ) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @PatchMapping("/change-password/{id}")
    public ResponseEntity<UserDto.Response> changePassword(
            @PathVariable UUID id,
            @Valid @RequestBody UserDto.ChangePassword request
    ) {
        return ResponseEntity.ok(userService.changePassword(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto.Response> deleteById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
