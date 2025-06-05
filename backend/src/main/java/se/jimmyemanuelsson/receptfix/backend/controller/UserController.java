package se.jimmyemanuelsson.receptfix.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.jimmyemanuelsson.receptfix.backend.dto.UserDto;
import se.jimmyemanuelsson.receptfix.backend.dto.UserRegisterDto;
import se.jimmyemanuelsson.receptfix.backend.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRegisterDto dto) {
        UserDto registeredUser = userService.register(dto);
        return ResponseEntity.ok(registeredUser);
    }
}
