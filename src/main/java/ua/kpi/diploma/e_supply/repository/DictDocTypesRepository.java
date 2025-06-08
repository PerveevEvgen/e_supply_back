package ua.kpi.diploma.e_supply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictDocTypes;

import java.util.UUID;

public interface DictDocTypesRepository extends JpaRepository<DictDocTypes, UUID> {
}
