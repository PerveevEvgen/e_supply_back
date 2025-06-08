package ua.kpi.diploma.e_supply.entity.dict_entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "dict_ranks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictRanks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String code;
    private String title;
}
