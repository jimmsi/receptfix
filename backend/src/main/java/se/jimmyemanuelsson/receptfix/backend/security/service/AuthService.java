package se.jimmyemanuelsson.receptfix.backend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import se.jimmyemanuelsson.receptfix.backend.dto.LoginRequestDto;
import se.jimmyemanuelsson.receptfix.backend.exceptions.InvalidLoginException;
import se.jimmyemanuelsson.receptfix.backend.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(LoginRequestDto loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            return jwtTokenProvider.generateToken(auth);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new InvalidLoginException();
        }

    }
}
