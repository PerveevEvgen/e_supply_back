package ua.kpi.diploma.e_supply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictItemStatuses;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictUnits;

import java.util.Optional;
import java.util.UUID;

public interface DictItemStatusesRepository extends JpaRepository<DictItemStatuses, UUID> {
    Optional<DictItemStatuses> findByCode(String code);
}
