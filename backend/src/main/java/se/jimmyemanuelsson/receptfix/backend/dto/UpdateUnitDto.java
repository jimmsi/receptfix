package se.jimmyemanuelsson.receptfix.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUnitDto(
        @NotBlank String name,
        @NotBlank String symbol
) {
}

