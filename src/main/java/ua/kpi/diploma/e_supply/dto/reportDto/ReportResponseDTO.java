package ua.kpi.diploma.e_supply.dto.reportDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ReportResponseDTO {
    private UUID itemId;
    private String itemName;
    private  String individualNumber;
    private String operationTypeName;
    private String fromUnitName;
    private String toUnitName;
    private String operationDate;
    private String documentName;
}
