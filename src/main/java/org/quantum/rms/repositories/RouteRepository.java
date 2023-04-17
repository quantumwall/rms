package org.quantum.rms.repositories;

import java.util.Optional;
import org.quantum.rms.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    Optional<Route> findByBillNumber(int billNumber);
}
