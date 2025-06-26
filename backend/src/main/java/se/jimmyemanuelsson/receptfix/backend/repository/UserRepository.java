package se.jimmyemanuelsson.receptfix.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jimmyemanuelsson.receptfix.backend.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
