package ua.kpi.diploma.e_supply.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictItemTypes;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictItemStatuses;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictUnits;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private LocalDate releaseDate;
    private String batchNumber;
    private String factoryNumber;
    private String nsnCode;
    private String individualNumber;

    @ManyToOne
    @JoinColumn(name = "type_code_id")
    private DictItemTypes typeCode;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private DictUnits unit;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private DictItemStatuses status;
}
