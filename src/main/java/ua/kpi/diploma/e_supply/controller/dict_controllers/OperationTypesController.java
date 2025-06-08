package ua.kpi.diploma.e_supply.controller.dict_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.kpi.diploma.e_supply.dto.dict_dto.OperationTypesDTO;
import ua.kpi.diploma.e_supply.service.impl.OperationTypesService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/operation-types")
@RequiredArgsConstructor
public class OperationTypesController {

    private final OperationTypesService service;

    @GetMapping
    public List<OperationTypesDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public OperationTypesDTO getById(@PathVariable UUID id) {
        return service.getById(id);
    }
}
