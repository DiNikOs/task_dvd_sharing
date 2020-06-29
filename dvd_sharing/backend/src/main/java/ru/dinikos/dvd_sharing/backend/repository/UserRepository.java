/**
 * @author Ostrovskiy Dmitriy
 * @created 20.06.2020
 * UserRepository
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.repository;

import ru.dinikos.dvd_sharing.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByUsername(String username);
    User findOneById(Long id);

    boolean existsByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
