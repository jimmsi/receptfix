package se.jimmyemanuelsson.receptfix.backend.service;

import se.jimmyemanuelsson.receptfix.backend.dto.UserDto;
import se.jimmyemanuelsson.receptfix.backend.dto.UserRegisterDto;

public interface UserService {
    UserDto register (UserRegisterDto dto);
}
