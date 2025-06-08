package ua.kpi.diploma.e_supply.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.diploma.e_supply.dto.dict_dto.ItemStatusesDTO;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictItemStatuses;
import ua.kpi.diploma.e_supply.repository.DictItemStatusesRepository;
import ua.kpi.diploma.e_supply.service.DictItemStatusesService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DictItemStatusesServiceImpl implements DictItemStatusesService {

    private final DictItemStatusesRepository repository;

    @Override
    public ItemStatusesDTO create(ItemStatusesDTO dto) {
        DictItemStatuses entity = new DictItemStatuses(null, dto.getCode(), dto.getTitle());
        return toDTO(repository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<ItemStatusesDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ItemStatusesDTO toDTO(DictItemStatuses entity) {
        return new ItemStatusesDTO(entity.getCode(), entity.getTitle());
    }
}
