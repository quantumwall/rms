package org.quantum.rms.repositories;

import java.util.Optional;

import org.quantum.rms.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
