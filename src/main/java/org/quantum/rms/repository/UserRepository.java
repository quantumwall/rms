package org.quantum.rms.repository;

import java.util.Optional;

import org.quantum.rms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
