package se.jimmyemanuelsson.receptfix.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.jimmyemanuelsson.receptfix.backend.dto.UserDto;
import se.jimmyemanuelsson.receptfix.backend.dto.UserRegisterDto;
import se.jimmyemanuelsson.receptfix.backend.model.Role;
import se.jimmyemanuelsson.receptfix.backend.model.User;
import se.jimmyemanuelsson.receptfix.backend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto register(UserRegisterDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .build();

        User saved = userRepository.save(user);

        return new UserDto(saved.getId(), saved.getEmail(), saved.getUsername(), saved.getRole());
    }
}
