package se.jimmyemanuelsson.receptfix.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import se.jimmyemanuelsson.receptfix.backend.dto.LoginRequestDto;
import se.jimmyemanuelsson.receptfix.backend.dto.UserMeDto;
import se.jimmyemanuelsson.receptfix.backend.security.service.AuthService;
import se.jimmyemanuelsson.receptfix.backend.security.service.UserDetailsImpl;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequest) {
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserMeDto> me(@AuthenticationPrincipal UserDetailsImpl user) {
        String email = user.getUsername();
        String role = user.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("UNKNOWN");

        return ResponseEntity.ok(new UserMeDto(email, role));
    }
}