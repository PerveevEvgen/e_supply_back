package ua.kpi.diploma.e_supply.dto.ItemsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsRequestDTO {
    private String title;
    private String individualNumber;
    private String nsnCode;
    private LocalDate releaseDate;
    private String batchNumber;
    private String factoryNumber;
    private UUID typeCodeId;
}
