package ua.kpi.diploma.e_supply.service;

import ua.kpi.diploma.e_supply.dto.dict_dto.DocTypesDTO;

import java.util.List;
import java.util.UUID;

public interface DictDocTypesService {

    DocTypesDTO create(DocTypesDTO dto);
    void delete(UUID id);
    List<DocTypesDTO> getAll();
}
