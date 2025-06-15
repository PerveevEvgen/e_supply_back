package ua.kpi.diploma.e_supply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.kpi.diploma.e_supply.dto.dict_dto.ItemCodesDTO;
import ua.kpi.diploma.e_supply.dto.dict_dto.OperationTypesDTO;
import ua.kpi.diploma.e_supply.dto.dict_dto.UnitsDTO;
import ua.kpi.diploma.e_supply.service.dictServices.ItemTypesService;
import ua.kpi.diploma.e_supply.service.dictServices.OperationTypesService;

import java.util.List;

@RestController
@RequestMapping("/api/dict")
@RequiredArgsConstructor
public class DictController {

    private final ua.kpi.diploma.e_supply.service.dictServices.UnitsService UnitsService;
    private final OperationTypesService OperationTypesService;
    private final ItemTypesService ItemTypesService;

    @GetMapping("/units")
    public List<UnitsDTO> getUnits() {
        return UnitsService.getAll();
    }

    @GetMapping("/operationTypes")
    public List<OperationTypesDTO> getOperationTypes() {
        return OperationTypesService.getAll();
    }
    @GetMapping("/itemTypes")
    public List<ItemCodesDTO> getTypes() {
        return ItemTypesService.getAll();
    }
}
