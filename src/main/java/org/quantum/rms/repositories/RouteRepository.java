package org.quantum.rms.repositories;

import java.util.List;

import org.quantum.rms.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("""
    	select r from Route r
    	where lower(r.departureCity) like lower(concat('%', :filter, '%'))
    	or lower(r.destinationCity) like lower(concat('%', :filter, '%'))
    	or lower(r.cargo.name) like lower(concat('%', :filter, '%'))
    	""")
    List<Route> search(@Param("filter") String filter);
}
