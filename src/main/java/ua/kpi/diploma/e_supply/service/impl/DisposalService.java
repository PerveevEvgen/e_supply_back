package ua.kpi.diploma.e_supply.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.diploma.e_supply.dto.DisposalRequestDTO;
import ua.kpi.diploma.e_supply.dto.ItemsDTO;
import ua.kpi.diploma.e_supply.entity.Documents;
import ua.kpi.diploma.e_supply.entity.ItemMoveLogs;
import ua.kpi.diploma.e_supply.entity.Items;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictItemStatuses;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictOperationTypes;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictUnits;
import ua.kpi.diploma.e_supply.repository.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisposalService {
    private final DocumentsRepository documentRepo;
    private final ItemsRepository itemsRepository;
    private final DictUnitsRepository dictUnitsRepository;
    private final DictItemStatusesRepository dictItemStatusesRepository;
    private final ItemMoveLogsRepository itemMoveLogsRepository;
    private final DictOperationTypesRepository dictOperationTypesRepository;
    private final S3Util S3Util;

    @Transactional
    public List<ItemsDTO> disposeItems(DisposalRequestDTO request, MultipartFile file) {

        String toUnitCode = "002";
        String itemStatus = "2";
        String operationCode  = "disposal";

        DictUnits toUnit = dictUnitsRepository.findByCode(toUnitCode)
                .orElseThrow(() -> new RuntimeException("Disposal unit not found"));

        DictItemStatuses disposedStatus = dictItemStatusesRepository.findByCode(itemStatus)
                .orElseThrow(() -> new RuntimeException("Disposed status not found"));

        DictOperationTypes operationType = dictOperationTypesRepository.findByCode(operationCode )
                .orElseThrow(() -> new RuntimeException("Disposal operation not found"));


        String fileUrl = saveFile(file);
        System.out.println(fileUrl);

        Documents document = new Documents();
        document.setLink(fileUrl);
        document = documentRepo.save(document);


        // Отримання всіх айтемів одним запитом
        List<Items> items = itemsRepository.findAllById(request.getItemIds());

        if (items.size() != request.getItemIds().size()) {
            throw new RuntimeException("Some items not found");
        }

        List<ItemsDTO> result = new ArrayList<>();

        for (Items item : items) {
            DictUnits fromUnit = item.getUnit();
            item.setStatus(disposedStatus);
            item.setUnit(toUnit);
            Items saved = itemsRepository.save(item);

            createDisposalLog(saved, fromUnit, toUnit, document, operationType);

            result.add(toDTO(saved));
        }

        return result;

    }
    private void createDisposalLog(Items item, DictUnits fromUnit, DictUnits toUnit, Documents document, DictOperationTypes operationType) {
        ItemMoveLogs log = new ItemMoveLogs();
        log.setItem(item);
        log.setFromUnit(fromUnit);
        log.setToUnit(toUnit);
        log.setMoveDate(LocalDate.now());
        log.setDocument(document);
        log.setOperationType(operationType);
        itemMoveLogsRepository.save(log);
    }
    private ItemsDTO toDTO(Items item) {
        return new ItemsDTO(
                item.getId(),
                item.getTitle(),
                item.getNsnCode(),
                item.getIndividualNumber(),
                item.getReleaseDate(),
                item.getBatchNumber(),
                item.getFactoryNumber(),
                item.getTypeCode().getId(),
                item.getUnit().getId(),
                item.getStatus().getId()
        );
    }
    public String saveFile(MultipartFile file) {
        try {
            return S3Util.uploadFile(
                    "disposal",
                    file.getOriginalFilename(),
                    file.getInputStream(),
                    file.getSize(),
                    file.getContentType()
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }
}

