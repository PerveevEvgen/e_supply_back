package ua.kpi.diploma.e_supply.dto.reportDto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ReportRequestDTO {

    private String operationCode;
    private LocalDate fromDate;
    private LocalDate toDate;
    private UUID fromUnitId;
    private UUID toUnitId;
    private String individualNumber;
}
