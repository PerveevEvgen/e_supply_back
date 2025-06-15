package ua.kpi.diploma.e_supply.service.dictServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.diploma.e_supply.dto.dict_dto.UnitsDTO;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictUnits;
import ua.kpi.diploma.e_supply.repository.DictUnitsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitsService {

    private final DictUnitsRepository repository;

    public List<UnitsDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private UnitsDTO toDTO(DictUnits entity) {
        return new UnitsDTO(entity.getId(), entity.getName(), entity.getCode());
    }
}
