package ua.kpi.diploma.e_supply.service;

import ua.kpi.diploma.e_supply.dto.dict_dto.ItemStatusesDTO;

import java.util.List;
import java.util.UUID;

public interface DictItemStatusesService {

    ItemStatusesDTO create(ItemStatusesDTO dto);
    void delete(UUID id);
    List<ItemStatusesDTO> getAll();
}
