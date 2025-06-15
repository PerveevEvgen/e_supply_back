package ua.kpi.diploma.e_supply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.diploma.e_supply.dto.ItemsDTO;
import ua.kpi.diploma.e_supply.dto.transferDto.TransferRequestDTO;
import ua.kpi.diploma.e_supply.entity.Documents;
import ua.kpi.diploma.e_supply.entity.Items;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictItemStatuses;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictOperationTypes;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictUnits;
import ua.kpi.diploma.e_supply.repository.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final ItemsRepository itemsRepository;
    private final DictUnitsRepository dictUnitsRepository;
    private final DictItemStatusesRepository dictItemStatusesRepository;
    private final ItemMoveLogsRepository itemMoveLogsRepository;
    private final DictOperationTypesRepository dictOperationTypesRepository;
    private final DocumentsRepository documentsRepository;
    private final S3Util s3Util;
    private final LoggerService loggerService;

    @Transactional
    public void transferItems(TransferRequestDTO request, MultipartFile file) {

         String itemStatus = "3";
         String operationCode = "transfer";

        DictUnits targetUnit = dictUnitsRepository.findByCode(request.getTargetUnitCode())
                .orElseThrow(() -> new RuntimeException("Target unit not found"));

        DictItemStatuses transferredStatus = dictItemStatusesRepository.findByCode(itemStatus)
                .orElseThrow(() -> new RuntimeException("Transferred status not found"));

        DictOperationTypes transferOperation = dictOperationTypesRepository.findByCode(operationCode)
                .orElseThrow(() -> new RuntimeException("Transfer operation not found"));

        String fileUrl = saveToS3(file);
        System.out.println(fileUrl);

        Documents document = new Documents();
        document.setLink(fileUrl);
        document = documentsRepository.save(document);

        List<Items> items = itemsRepository.findAllById(request.getItemIds());

        if (items.size() != request.getItemIds().size()) {
            throw new RuntimeException("Some items not found");
        }

        List<ItemsDTO> result = new ArrayList<>();

        for (Items item : items) {
            DictUnits fromUnit = item.getUnit();

            item.setUnit(targetUnit);
            item.setStatus(transferredStatus);
            Items saved = itemsRepository.save(item);

            loggerService.logMovement(saved, fromUnit, targetUnit, document, transferOperation);
//            createTransferLog(saved, fromUnit, targetUnit, document, transferOperation);

            result.add(toDTO(saved));
        }

    }


//    private void createTransferLog(Items item, DictUnits fromUnit, DictUnits toUnit, Documents document, DictOperationTypes operationType) {
//        ItemMoveLogs log = new ItemMoveLogs();
//        log.setItem(item);
//        log.setFromUnit(fromUnit);
//        log.setToUnit(toUnit);
//        log.setMoveDate(LocalDate.now());
//        log.setDocument(document);
//        log.setOperationType(operationType);
//        itemMoveLogsRepository.save(log);
//    }

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

    @Transactional(readOnly = true)
    public List<ItemsDTO> getAvailableForTransferItems() {

        List<Items> items = itemsRepository.findAllByStatusCode("1");

        return items.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public String saveToS3(MultipartFile file) {
        try {
            return s3Util.uploadFile(
                    "transfer",
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