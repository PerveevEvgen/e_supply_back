package ua.kpi.diploma.e_supply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.diploma.e_supply.dto.reportDto.ReportRequestDTO;
import ua.kpi.diploma.e_supply.dto.reportDto.ReportResponseDTO;
import ua.kpi.diploma.e_supply.entity.ItemMoveLogs;
import ua.kpi.diploma.e_supply.repository.ItemMoveLogsRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ItemMoveLogsRepository itemMoveLogsRepo;

    public List<ReportResponseDTO> getReport(ReportRequestDTO request) {
        String operationCode = request.getOperationCode();
        LocalDate fromDate = request.getFromDate();
        LocalDate toDate = request.getToDate();
        UUID fromUnitId = request.getFromUnitId();
        UUID toUnitId = request.getToUnitId();
        String individualNumber = request.getIndividualNumber();

        return itemMoveLogsRepo.findAll().stream()
                .filter(log -> operationCode == null || (log.getOperationType() != null && operationCode.equalsIgnoreCase(log.getOperationType().getCode())))
                .filter(log -> fromDate == null || !log.getMoveDate().isBefore(fromDate))
                .filter(log -> toDate == null || !log.getMoveDate().isAfter(toDate))
                .filter(log -> fromUnitId == null || (log.getFromUnit() != null && fromUnitId.equals(log.getFromUnit().getId())))
                .filter(log -> toUnitId == null || (log.getToUnit() != null && toUnitId.equals(log.getToUnit().getId())))
                .filter(log -> individualNumber == null || individualNumber.equalsIgnoreCase(log.getItem().getIndividualNumber()))
                .map(this::toDto)
                .toList();
    }

    private ReportResponseDTO toDto(ItemMoveLogs log) {
        return new ReportResponseDTO(
                log.getItem().getId(),
                log.getItem().getTitle(),
                log.getItem().getIndividualNumber(),
                log.getOperationType() != null ? log.getOperationType().getTitle() : null,
                log.getFromUnit() != null ? log.getFromUnit().getName() : null,
                log.getToUnit() != null ? log.getToUnit().getName() : null,
                Date.valueOf(log.getMoveDate()),
                log.getDocument() != null ? log.getDocument().getLink() : null
        );
    }
}
