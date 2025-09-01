package se.jimmyemanuelsson.receptfix.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jimmyemanuelsson.receptfix.backend.dto.UnitDto;
import se.jimmyemanuelsson.receptfix.backend.mapper.UnitMapper;
import se.jimmyemanuelsson.receptfix.backend.repository.UnitRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UserService userService;

    @Override
    public List<UnitDto> listUnitsForCurrentUser() {
        UUID userId = userService.getCurrentUserId();
        return unitRepository.findByUserIsNullOrUser_Id(userId).stream()
                .map(u -> UnitMapper.toDto(u, userId))
                .toList();
    }
}
