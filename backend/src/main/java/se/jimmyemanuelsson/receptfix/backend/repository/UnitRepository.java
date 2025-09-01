package se.jimmyemanuelsson.receptfix.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jimmyemanuelsson.receptfix.backend.model.Unit;

import java.util.List;
import java.util.UUID;

public interface UnitRepository extends JpaRepository<Unit, UUID> {

    List<Unit> findByUserIsNullOrUser_Id(UUID userId);

    boolean existsByUser_IdAndNameIgnoreCase(UUID userId, String name);
    boolean existsByUserIsNullAndNameIgnoreCase(String name);
}
