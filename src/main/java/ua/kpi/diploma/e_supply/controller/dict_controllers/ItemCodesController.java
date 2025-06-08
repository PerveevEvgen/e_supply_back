package ua.kpi.diploma.e_supply.controller.dict_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.kpi.diploma.e_supply.dto.dict_dto.ItemCodesDTO;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictItemTypes;
import ua.kpi.diploma.e_supply.service.impl.ItemTypesService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dict/item-types")
@RequiredArgsConstructor
public class ItemCodesController {

    private final ItemTypesService service;

    @GetMapping
    public List<ItemCodesDTO> getAll() {
        return service.getAll();
    }
}
