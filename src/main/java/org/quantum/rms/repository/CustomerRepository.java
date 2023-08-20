package org.quantum.rms.repository;

import java.util.List;

import org.quantum.rms.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByUserId(Long userId);
}
