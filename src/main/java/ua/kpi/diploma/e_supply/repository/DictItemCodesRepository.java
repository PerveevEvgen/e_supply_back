package ua.kpi.diploma.e_supply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictItemTypes;

import java.util.Optional;
import java.util.UUID;

public interface DictItemCodesRepository extends JpaRepository<DictItemTypes, UUID> {
     Optional<DictItemTypes> findByCode(String code);
}
