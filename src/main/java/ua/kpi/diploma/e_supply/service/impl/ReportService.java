package ua.kpi.diploma.e_supply.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.diploma.e_supply.dto.reportDto.ReportRequestDTO;
import ua.kpi.diploma.e_supply.dto.reportDto.ReportResponseDTO;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictOperationTypes;
import ua.kpi.diploma.e_supply.repository.DictOperationTypesRepository;
import ua.kpi.diploma.e_supply.repository.ItemMoveLogsRepository;
import ua.kpi.diploma.e_supply.repository.ItemsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ItemMoveLogsRepository itemMoveLogsRepo;
    private final ItemsRepository itemsRepo;
    private final DictOperationTypesRepository operationTypeRepo;

    public List<ReportResponseDTO> getReport(s request) {

        String operationCode = request.getOperationCode();

        UUID operationId = operationTypeRepo.findIdByCode(operationCode)
                .orElseThrow(() -> new RuntimeException("Operation id with '" + operationCode + "' not found"));

        LocalDate from = request.getFromDate();
        LocalDate to = request.getToDate();
        UUID fromUnitId = request.getFromUnitId();
        UUID toUnitId = request.getToUnitId();
        String individualNumber = request.getIndividualNumber();

        return itemMoveLogsRepo.findAll().stream()
                .filter(log -> operationType == null || log.getOperationType().getCode().equalsIgnoreCase(operationCode))
                .filter(log -> {
                    LocalDate date = log.getMoveDate();
                    boolean afterFrom = from == null || !date.isBefore(from);
                    boolean beforeTo = to == null || !date.isAfter(to);
                    return afterFrom && beforeTo;
                })
                .filter(log -> fromUnitId == null || (log.getFromUnit() != null && fromUnitId.equals(log.getFromUnit().getId())))
                .filter(log -> toUnitId == null || (log.getToUnit() != null && toUnitId.equals(log.getToUnit().getId())))
                .filter(log -> individualNumber == null || individualNumber.equalsIgnoreCase(log.getItem().getIndividualNumber()))
                .map(log -> new ReportResponseDTO(
                        log.getId(),
                        log.getItem().getTitle(),
                        log.getOperationType().getTitle(),
                        log.getFromUnit().getName(),
                        log.getToUnit().getName(),
                        log.getMoveDate().toString(),
                        log.getDocument().getLink()
                ))
                .toList();
    }

}
