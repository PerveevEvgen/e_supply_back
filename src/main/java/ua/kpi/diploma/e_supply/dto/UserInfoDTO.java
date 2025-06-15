package ua.kpi.diploma.e_supply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserInfoDTO {
    private String firstName;
    private String lastName;
    private String patronomicName;
    private LocalDate creationDate;
    private String rank;
    private String position;
    private String unit;
    private String role;
    private String email;
}