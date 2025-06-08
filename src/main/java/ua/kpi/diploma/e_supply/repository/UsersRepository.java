package ua.kpi.diploma.e_supply.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.diploma.e_supply.entity.Users;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findById(UUID id);
}
