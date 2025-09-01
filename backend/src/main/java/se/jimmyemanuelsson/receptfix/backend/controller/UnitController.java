package se.jimmyemanuelsson.receptfix.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import se.jimmyemanuelsson.receptfix.backend.dto.UnitDto;
import se.jimmyemanuelsson.receptfix.backend.service.UnitService;

import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping
    public List<UnitDto> listUnits() {
        return unitService.listUnitsForCurrentUser();
    }
}
