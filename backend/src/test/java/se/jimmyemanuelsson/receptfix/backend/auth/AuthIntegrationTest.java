package se.jimmyemanuelsson.receptfix.backend.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import se.jimmyemanuelsson.receptfix.backend.dto.LoginRequestDto;
import se.jimmyemanuelsson.receptfix.backend.model.Role;
import se.jimmyemanuelsson.receptfix.backend.model.User;
import se.jimmyemanuelsson.receptfix.backend.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String userEmail = "test@example.com";
    private final String userPassword = "password123";
    private final String adminEmail = "admin@example.com";
    private final String adminPassword = "adminpass";

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        User user = User.builder()
                .email(userEmail)
                .username("testuser")
                .password(passwordEncoder.encode(userPassword))
                .role(Role.USER)
                .build();

        User adminUser = User.builder()
                .email(adminEmail)
                .username("adminuser")
                .password(passwordEncoder.encode(adminPassword))
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);
        userRepository.save(adminUser);
    }

    private String getTokenForUser(String email, String password) throws Exception {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("token").asText();
    }

    @Test
    void login_succeeds_with_valid_credentials() throws Exception {
        LoginRequestDto request = new LoginRequestDto();
        request.setEmail(userEmail);
        request.setPassword(userPassword);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void login_fails_with_invalid_credentials() throws Exception {
        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("nonexistent@example.com");
        request.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("auth/invalid-credentials"))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.statusCode").value(401));
    }

    @Test
    void me_returns_user_info_with_valid_token() throws Exception {
        String token = getTokenForUser(userEmail, userPassword);

        mockMvc.perform(get("/api/auth/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userEmail))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @Test
    void access_protected_endpoint_without_token_returns_unauthorized() throws Exception {
        mockMvc.perform(get("/api/test/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void access_admin_endpoint_as_user_returns_forbidden() throws Exception {
        String token = getTokenForUser(userEmail, userPassword);

        mockMvc.perform(get("/api/test/admin")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void access_admin_endpoint_as_admin_returns_ok() throws Exception {
        String token = getTokenForUser(adminEmail, adminPassword);

        mockMvc.perform(get("/api/test/admin")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }
}
