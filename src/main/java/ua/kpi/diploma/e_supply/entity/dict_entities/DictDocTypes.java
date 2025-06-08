package ua.kpi.diploma.e_supply.entity.dict_entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "dict_doc_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictDocTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Short code;
    private String title;
}
