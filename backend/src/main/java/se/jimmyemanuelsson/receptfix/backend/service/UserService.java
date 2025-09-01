package se.jimmyemanuelsson.receptfix.backend.service;

import se.jimmyemanuelsson.receptfix.backend.dto.UserDto;
import se.jimmyemanuelsson.receptfix.backend.dto.UserRegisterDto;
import se.jimmyemanuelsson.receptfix.backend.model.User;

import java.util.UUID;

public interface UserService {
    UserDto register (UserRegisterDto dto);
    User getCurrentUser();
    UUID getCurrentUserId();
}
