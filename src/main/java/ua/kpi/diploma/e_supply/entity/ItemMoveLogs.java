package ua.kpi.diploma.e_supply.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictOperationTypes;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictUnits;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "item_move_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMoveLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Items item;

    @ManyToOne
    @JoinColumn(name = "from_unit_id")
    private DictUnits fromUnit;

    @ManyToOne
    @JoinColumn(name = "to_unit_id")
    private DictUnits toUnit;

    private LocalDate moveDate;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Documents document;

    @ManyToOne
    @JoinColumn(name = "operation_type_id")
    private DictOperationTypes operationType;
}
