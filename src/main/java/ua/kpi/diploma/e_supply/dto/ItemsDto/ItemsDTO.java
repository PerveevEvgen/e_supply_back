package ua.kpi.diploma.e_supply.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsDTO {

    private UUID id;
    private String title;
    private String nsnCode;
    private String individualNumber;
    private LocalDate releaseDate;
    private String batchNumber;
    private String factoryNumber;
    private UUID typeCodeId;
    private UUID unitId;
    private UUID statusId;
}
