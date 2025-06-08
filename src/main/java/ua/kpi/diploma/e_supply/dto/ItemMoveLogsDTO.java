package ua.kpi.diploma.e_supply.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMoveLogsDTO {

    private UUID id;
    private UUID itemId;
    private UUID fromUnitId;
    private UUID toUnitId;
    private LocalDate moveDate;
    private UUID documentId;
    private UUID operationTypeId;
}
