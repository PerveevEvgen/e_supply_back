package ua.kpi.diploma.e_supply.service;

import ua.kpi.diploma.e_supply.dto.DocumentsDTO;

import java.util.List;
import java.util.UUID;

public interface DocumentService {
    DocumentsDTO create(DocumentsDTO dto);
    void delete(UUID id);
    List<DocumentsDTO> getAll();
    DocumentsDTO getById(UUID id);
}
