package ua.kpi.diploma.e_supply.controller.dict_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.kpi.diploma.e_supply.dto.dict_dto.UnitsDTO;
import ua.kpi.diploma.e_supply.service.DictUnitsService;

import java.util.List;

@RestController
@RequestMapping("/api/dict/units")
@RequiredArgsConstructor
public class UnitsController {

    private final DictUnitsService service;

    @GetMapping
    public List<UnitsDTO> getAll() {
        return service.getAll();
    }
}
