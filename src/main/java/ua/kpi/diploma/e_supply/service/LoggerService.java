package ua.kpi.diploma.e_supply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.diploma.e_supply.entity.ItemMoveLogs;
import ua.kpi.diploma.e_supply.entity.Items;
import ua.kpi.diploma.e_supply.entity.Documents;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictUnits;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictOperationTypes;
import ua.kpi.diploma.e_supply.repository.ItemMoveLogsRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoggerService {

    private final ItemMoveLogsRepository logRepository;

    public void logMovement(Items item,
                            DictUnits fromUnit,
                            DictUnits toUnit,
                            Documents document,
                            DictOperationTypes operationType) {
        ItemMoveLogs log = new ItemMoveLogs();
        log.setItem(item);
        log.setFromUnit(fromUnit);
        log.setToUnit(toUnit);
        log.setMoveDate(LocalDate.now());
        log.setDocument(document);
        log.setOperationType(operationType);

        logRepository.save(log);
    }
}
