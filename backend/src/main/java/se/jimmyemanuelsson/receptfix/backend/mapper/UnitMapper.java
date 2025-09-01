package se.jimmyemanuelsson.receptfix.backend.mapper;

import se.jimmyemanuelsson.receptfix.backend.dto.UnitDto;
import se.jimmyemanuelsson.receptfix.backend.model.Unit;
import java.util.UUID;

public final class UnitMapper {
    private UnitMapper() {}

    public static UnitDto toDto(Unit entity, UUID currentUserId) {
        boolean owned = entity.getUser() != null && entity.getUser().getId().equals(currentUserId);
        return new UnitDto(entity.getId(), entity.getName(), entity.getSymbol(), owned);
    }
}
