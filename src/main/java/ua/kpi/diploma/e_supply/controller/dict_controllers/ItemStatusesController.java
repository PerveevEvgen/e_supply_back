package ua.kpi.diploma.e_supply.controller.dict_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.kpi.diploma.e_supply.dto.dict_dto.ItemStatusesDTO;
import ua.kpi.diploma.e_supply.service.DictItemStatusesService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dict/item-statuses")
@RequiredArgsConstructor
public class ItemStatusesController {

    private final DictItemStatusesService service;

    @PostMapping
    public ItemStatusesDTO create(@RequestBody ItemStatusesDTO dto) {
        return service.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping
    public List<ItemStatusesDTO> getAll() {
        return service.getAll();
    }
}
