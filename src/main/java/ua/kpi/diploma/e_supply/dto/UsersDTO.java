package ua.kpi.diploma.e_supply.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String patronomicName;
    private LocalDate creationDate;
    private UUID rankId;
    private UUID positionId;
    private UUID unitId;
    private UUID roleId;
}
