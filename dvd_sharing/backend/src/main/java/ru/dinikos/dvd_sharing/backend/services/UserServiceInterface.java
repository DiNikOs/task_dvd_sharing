/**
 * @author Ostrovskiy Dmitriy
 * @created 20.06.2020
 * UserService
 * @version v1.0
 */


package ru.dinikos.dvd_sharing.backend.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.dinikos.dvd_sharing.backend.entities.User;
import ru.dinikos.dvd_sharing.backend.controllers.dto.SystemUser;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {

    Long findIdByUserName(String username);
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();

    public boolean checkPassword(User user, String password);
    boolean isUsernameExist(String username);

    User save(SystemUser systemUser);
    void delete(Long id);
}
