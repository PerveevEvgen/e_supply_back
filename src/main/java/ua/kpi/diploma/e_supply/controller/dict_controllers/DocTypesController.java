package ua.kpi.diploma.e_supply.controller.dict_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.kpi.diploma.e_supply.dto.dict_dto.DocTypesDTO;
import ua.kpi.diploma.e_supply.service.DictDocTypesService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dict/doc-types")
@RequiredArgsConstructor
public class DocTypesController {

    private final DictDocTypesService service;

    @PostMapping
    public DocTypesDTO create(@RequestBody DocTypesDTO dto) {
        return service.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping
    public List<DocTypesDTO> getAll() {
        return service.getAll();
    }
}
