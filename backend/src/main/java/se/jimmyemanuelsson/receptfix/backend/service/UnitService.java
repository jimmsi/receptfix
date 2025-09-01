package se.jimmyemanuelsson.receptfix.backend.service;

import se.jimmyemanuelsson.receptfix.backend.dto.UnitDto;

import java.util.List;

public interface UnitService {
    List<UnitDto> listUnitsForCurrentUser();
}
