package ua.kpi.diploma.e_supply.entity.dict_entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "dict_units")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictUnits {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String code;
}
