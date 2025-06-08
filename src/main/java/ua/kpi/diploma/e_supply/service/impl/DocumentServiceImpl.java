package ua.kpi.diploma.e_supply.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.diploma.e_supply.dto.DocumentsDTO;
import ua.kpi.diploma.e_supply.entity.Documents;
import ua.kpi.diploma.e_supply.repository.DocumentsRepository;
import ua.kpi.diploma.e_supply.service.DocumentService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentsRepository documentsRepository;

    @Override
    public DocumentsDTO create(DocumentsDTO dto) {
        Documents doc = new Documents();
        doc.setLink(dto.getLink());
        Documents saved = documentsRepository.save(doc);
        return new DocumentsDTO(saved.getId(), saved.getLink());
    }

    @Override
    public void delete(UUID id) {
        documentsRepository.deleteById(id);
    }

    @Override
    public List<DocumentsDTO> getAll() {
        return documentsRepository.findAll().stream()
                .map(doc -> new DocumentsDTO(doc.getId(), doc.getLink()))
                .collect(Collectors.toList());
    }

    @Override
    public DocumentsDTO getById(UUID id) {
        Documents doc = documentsRepository.findById(id).orElseThrow();
        return new DocumentsDTO(doc.getId(), doc.getLink());
    }
}
