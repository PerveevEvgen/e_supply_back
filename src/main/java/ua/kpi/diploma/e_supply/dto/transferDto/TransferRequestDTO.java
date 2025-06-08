package ua.kpi.diploma.e_supply.dto.transferDto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TransferRequestDTO {
    private List<UUID> itemIds;
    private String targetUnitCode;
}
