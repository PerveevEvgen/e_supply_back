package ua.kpi.diploma.e_supply.controller.dict_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.kpi.diploma.e_supply.dto.DocumentsDTO;
import ua.kpi.diploma.e_supply.service.DocumentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public DocumentsDTO create(@RequestBody DocumentsDTO dto) {
        return documentService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        documentService.delete(id);
    }

    @GetMapping
    public List<DocumentsDTO> getAll() {
        return documentService.getAll();
    }

    @GetMapping("/{id}")
    public DocumentsDTO getById(@PathVariable UUID id) {
        return documentService.getById(id);
    }
}
