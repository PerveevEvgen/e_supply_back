package ua.kpi.diploma.e_supply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.diploma.e_supply.entity.Documents;

import java.util.UUID;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, UUID> {
    // Можна додати методи типу: Optional<Documents> findByLink(String link);
}
