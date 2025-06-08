package ua.kpi.diploma.e_supply.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.diploma.e_supply.dto.ItemsDTO;
import ua.kpi.diploma.e_supply.dto.ItemsDto.ItemsRequestDTO;
import ua.kpi.diploma.e_supply.entity.*;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictItemTypes;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictItemStatuses;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictOperationTypes;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictUnits;
import ua.kpi.diploma.e_supply.repository.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcceptanceServiceImpl {

    private final ItemsRepository itemsRepository;
    private final DictItemCodesRepository typeCodeRepo;
    private final DictUnitsRepository unitRepo;
    private final DictItemStatusesRepository statusRepo;

    private final DocumentsRepository documentRepo;
    private final DictOperationTypesRepository operationTypeRepo;
    private final ItemMoveLogsRepository logRepo;
    private final S3Util S3Util;

    @Transactional
    public List<ItemsDTO> accept(List<ItemsRequestDTO> dtos, MultipartFile file) {
        String toUnitCode = "001";
        String fromUnitCode = "000";
        String operationCode = "income";
        String itemStatusCode = "1";

        // Словники (один раз)
        DictUnits toUnit = unitRepo.findByCode(toUnitCode)
                .orElseThrow(() -> new RuntimeException("Unit with code '" + toUnitCode + "' not found"));
        DictUnits fromUnit = unitRepo.findByCode(fromUnitCode)
                .orElseThrow(() -> new RuntimeException("Unit with code '" + fromUnitCode + "' not found"));
        DictOperationTypes operationType = operationTypeRepo.findByCode(operationCode)
                .orElseThrow(() -> new RuntimeException("Operation type with code '" + operationCode + "' not found"));
        DictItemStatuses status = statusRepo.findByCode(itemStatusCode)
                .orElseThrow(() -> new RuntimeException("Status with code '" + itemStatusCode + "' not found"));

        String fileUrl = saveToS3(file);
        System.out.println(fileUrl);

        Documents document = new Documents();
        document.setLink(fileUrl);
        document = documentRepo.save(document);

        List<ItemsDTO> result = new ArrayList<>();

        for (ItemsRequestDTO dto : dtos) {
            // Витяг typeCode для кожного айтема
            DictItemTypes typeCode = typeCodeRepo.findById(dto.getTypeCodeId())
                    .orElseThrow(() -> new RuntimeException("Item type not found: " + dto.getTypeCodeId()));


            Items savedItem = createItem(dto, typeCode, toUnit, status);
            createLog(savedItem, fromUnit, toUnit, document, operationType);

            result.add(toDTO(savedItem));
        }

        return result;
    }


    private Items createItem(ItemsRequestDTO dto, DictItemTypes typeCode, DictUnits unit, DictItemStatuses status) {
        Items item = new Items();
        item.setTitle(dto.getTitle());
        item.setReleaseDate(dto.getReleaseDate());
        item.setBatchNumber(dto.getBatchNumber());
        item.setFactoryNumber(dto.getFactoryNumber());
        item.setNsnCode(dto.getNsnCode());
        item.setIndividualNumber(dto.getIndividualNumber());
        item.setTypeCode(typeCode);
        item.setUnit(unit);
        item.setStatus(status);
        itemsRepository.save(item);
        return item;
    }

    private void createLog(Items item, DictUnits fromUnit, DictUnits toUnit, Documents document, DictOperationTypes operationType) {
        ItemMoveLogs log = new ItemMoveLogs();
        log.setItem(item);
        log.setFromUnit(fromUnit);
        log.setToUnit(toUnit);
        log.setMoveDate(LocalDate.now());
        log.setDocument(document);
        log.setOperationType(operationType);
        logRepo.save(log);
    }

    public ItemsDTO update(UUID id, ItemsDTO dto) {
        Items item = itemsRepository.findById(id).orElseThrow();
        item.setTitle(dto.getTitle());
        item.setReleaseDate(dto.getReleaseDate());
        item.setBatchNumber(dto.getBatchNumber());
        item.setFactoryNumber(dto.getFactoryNumber());
        item.setTypeCode(typeCodeRepo.findById(dto.getTypeCodeId()).orElseThrow());
        item.setUnit(unitRepo.findById(dto.getUnitId()).orElseThrow());
        item.setStatus(statusRepo.findById(dto.getStatusId()).orElseThrow());
        return toDTO(itemsRepository.save(item));
    }

    public List<ItemsDTO> getAll() {
        return itemsRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
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

    public String saveToS3(MultipartFile file) {
        try {
            return S3Util.uploadFile(
                    "acceptance",
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
