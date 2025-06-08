package ua.kpi.diploma.e_supply.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DisposalRequestDTO {
    private List<UUID> itemIds;
}