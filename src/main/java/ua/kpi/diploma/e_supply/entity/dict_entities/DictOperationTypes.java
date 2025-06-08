package ua.kpi.diploma.e_supply.entity.dict_entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "dict_operation_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictOperationTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private String code;
}
