package ua.kpi.diploma.e_supply.service.dictServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.diploma.e_supply.dto.dict_dto.OperationTypesDTO;
import ua.kpi.diploma.e_supply.repository.DictOperationTypesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationTypesService {

    private final DictOperationTypesRepository repository;

    public List<OperationTypesDTO> getAll() {
        return repository.findAll().stream()
                .map(op -> new OperationTypesDTO(op.getId(), op.getTitle(), op.getCode()))
                .collect(Collectors.toList());
    }
}
