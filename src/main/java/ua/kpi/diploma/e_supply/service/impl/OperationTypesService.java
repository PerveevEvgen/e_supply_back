package ua.kpi.diploma.e_supply.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.diploma.e_supply.dto.dict_dto.OperationTypesDTO;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictOperationTypes;
import ua.kpi.diploma.e_supply.repository.DictOperationTypesRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationTypesService {

    private final DictOperationTypesRepository repository;

    public OperationTypesDTO create(OperationTypesDTO dto) {
        DictOperationTypes entity = new DictOperationTypes(null, dto.getTitle(), dto.getCode());
        DictOperationTypes saved = repository.save(entity);
        return new OperationTypesDTO(saved.getId(), saved.getTitle(), saved.getCode());
    }

    public OperationTypesDTO getById(UUID id) {
        DictOperationTypes op = repository.findById(id).orElseThrow();
        return new OperationTypesDTO(op.getId(), op.getTitle(), op.getCode());
    }

    public List<OperationTypesDTO> getAll() {
        return repository.findAll().stream()
                .map(op -> new OperationTypesDTO(op.getId(), op.getTitle(), op.getCode()))
                .collect(Collectors.toList());
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
