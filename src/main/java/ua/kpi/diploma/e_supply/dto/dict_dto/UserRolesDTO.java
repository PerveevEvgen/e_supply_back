package ua.kpi.diploma.e_supply.dto.dict_dto;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesDTO {

    private UUID id;
    private String code;
    private String title;
}
