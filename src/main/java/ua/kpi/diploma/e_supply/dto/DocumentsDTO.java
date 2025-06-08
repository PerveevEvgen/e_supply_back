package ua.kpi.diploma.e_supply.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsDTO {

    private UUID id;
    private String link;
}
