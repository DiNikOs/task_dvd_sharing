/**
 * @author Ostrovskiy Dmitriy
 * @created 20.06.2020
 * RoleRepository
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dinikos.dvd_sharing.backend.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findOneByName(String name);
}
