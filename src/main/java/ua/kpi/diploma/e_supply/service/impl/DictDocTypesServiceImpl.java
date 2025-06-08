package ua.kpi.diploma.e_supply.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.diploma.e_supply.dto.dict_dto.DocTypesDTO;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictDocTypes;
import ua.kpi.diploma.e_supply.repository.DictDocTypesRepository;
import ua.kpi.diploma.e_supply.service.DictDocTypesService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DictDocTypesServiceImpl implements DictDocTypesService {

    private final DictDocTypesRepository repository;

    @Override
    public DocTypesDTO create(DocTypesDTO dto) {
        DictDocTypes entity = new DictDocTypes(null, dto.getCode(), dto.getTitle());
        return toDTO(repository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<DocTypesDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private DocTypesDTO toDTO(DictDocTypes entity) {
        return new DocTypesDTO(entity.getCode(), entity.getTitle());
    }
}
