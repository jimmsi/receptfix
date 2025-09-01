package se.jimmyemanuelsson.receptfix.backend.dto;

import java.util.UUID;

public record UnitDto (
    UUID id,
    String name,
    String symbol,
    boolean ownedByCurrentUser
) {}
