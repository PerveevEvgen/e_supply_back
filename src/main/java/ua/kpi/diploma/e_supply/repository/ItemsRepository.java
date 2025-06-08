package ua.kpi.diploma.e_supply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.kpi.diploma.e_supply.entity.Items;

import java.util.List;
import java.util.UUID;

public interface ItemsRepository extends JpaRepository<Items, UUID> {
    @Query("SELECT i FROM Items i WHERE i.status.code = :code")
    List<Items> findAllByStatusCode(@Param("code") String code);

}
