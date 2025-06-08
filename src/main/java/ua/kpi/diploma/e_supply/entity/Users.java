package ua.kpi.diploma.e_supply.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictPositions;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictRanks;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictUnits;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictUserRoles;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
    private String patronomicName;
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "rank_id")
    private DictRanks rank;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private DictPositions position;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private DictUnits unit;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private DictUserRoles role;
}
