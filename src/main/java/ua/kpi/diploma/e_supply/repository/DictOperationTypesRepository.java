package ua.kpi.diploma.e_supply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kpi.diploma.e_supply.entity.dict_entities.DictOperationTypes;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DictOperationTypesRepository extends JpaRepository<DictOperationTypes, UUID> {
    Optional<DictOperationTypes> findByCode(String code);

    @Query("SELECT o.id FROM DictOperationTypes o WHERE o.code = :code")
    Optional<UUID> findIdByCode(@Param("code") String code);

}
