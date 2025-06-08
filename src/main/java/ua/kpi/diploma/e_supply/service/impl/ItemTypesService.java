package ua.kpi.diploma.e_supply.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.diploma.e_supply.dto.dict_dto.ItemCodesDTO;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictItemTypes;
import ua.kpi.diploma.e_supply.repository.DictItemCodesRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemTypesService{

    private final DictItemCodesRepository repository;

    public List<ItemCodesDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ItemCodesDTO toDTO(DictItemTypes entity) {
        return new ItemCodesDTO(entity.getCode(), entity.getTitle());
    }
}
