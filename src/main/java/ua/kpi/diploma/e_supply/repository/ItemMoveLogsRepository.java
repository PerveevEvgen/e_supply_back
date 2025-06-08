package ua.kpi.diploma.e_supply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.diploma.e_supply.entity.ItemMoveLogs;

import java.util.UUID;

@Repository
public interface ItemMoveLogsRepository extends JpaRepository<ItemMoveLogs, UUID> {

}
