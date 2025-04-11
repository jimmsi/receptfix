package se.jimmyemanuelsson.receptfix.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "unit_conversions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitConversion {

    @EmbeddedId
    private UnitConversionId id;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @ManyToOne
    @MapsId("fromUnitId")
    @JoinColumn(name = "from_unit_id", nullable = false)
    private Unit fromUnit;

    @ManyToOne
    @MapsId("toUnitId")
    @JoinColumn(name = "to_unit_id", nullable = false)
    private Unit toUnit;

    @Column(name = "conversion_factor", nullable = false)
    private BigDecimal conversionFactor;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

