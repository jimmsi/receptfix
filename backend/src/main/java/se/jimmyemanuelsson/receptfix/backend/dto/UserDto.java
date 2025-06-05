package se.jimmyemanuelsson.receptfix.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import se.jimmyemanuelsson.receptfix.backend.model.Role;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private Role role;
}