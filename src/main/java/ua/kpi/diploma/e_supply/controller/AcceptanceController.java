package ua.kpi.diploma.e_supply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.diploma.e_supply.dto.ItemsDTO;
import ua.kpi.diploma.e_supply.dto.ItemsDto.ItemsRequestDTO;
import ua.kpi.diploma.e_supply.service.impl.AcceptanceServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/acceptance")
@RequiredArgsConstructor
public class AcceptanceController {

    private final AcceptanceServiceImpl service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ItemsDTO> create(@RequestPart("request") List<ItemsRequestDTO> dtos,
                                 @RequestPart("file") MultipartFile file) {
        return service.accept(dtos, file);
    }

    @PutMapping("/{id}")
    public ItemsDTO update(@PathVariable UUID id, @RequestBody ItemsDTO dto) {
        return service.update(id, dto);
    }


    @GetMapping
    public List<ItemsDTO> getAll() {
        return service.getAll();
    }

}
