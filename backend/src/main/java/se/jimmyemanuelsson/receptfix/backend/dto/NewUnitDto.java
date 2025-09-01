package se.jimmyemanuelsson.receptfix.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUnitDto(
        @NotBlank String name,
        @NotBlank String symbol
) {}