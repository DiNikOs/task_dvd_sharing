/**
 * @author Ostrovskiy Dmitriy
 * @created 20.06.2020
 * UserServiceImpl
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dinikos.dvd_sharing.backend.entities.Disk;
import ru.dinikos.dvd_sharing.backend.entities.Role;
import ru.dinikos.dvd_sharing.backend.entities.User;
import ru.dinikos.dvd_sharing.backend.repository.RoleRepository;
import ru.dinikos.dvd_sharing.backend.repository.UserRepository;
import ru.dinikos.dvd_sharing.backend.controllers.dto.SystemUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private DiskService diskServiceImpl;
    private List<Disk> disks;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       DiskService diskServiceImpl) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.diskServiceImpl = diskServiceImpl;
    }
    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = findByUsername(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return userRepository.findOneById(id);
    }

    @Override
    @Transactional
    public Long findIdByUserName(String username) {
        return userRepository.findOneByUsername(username).getId();
    }

    /**
     * @author Ostrovskiy Dmitriy
     * @created 20.06.2020
     * Метод загружает User по имени
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public User save(SystemUser systemUser) {
        User user = new User();
        if (findByUsername(systemUser.getUsername()) != null) {
            return null;
        }
        user.setUsername(systemUser.getUsername());
        if (systemUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        }
        user.setFirstName(systemUser.getFirstName());
        Collection<Role> roles = new ArrayList<>();
        Role role = roleRepository.findOneByName("ROLE_USER");
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        if (findByUsername(user.getUsername()) == null) {
            throw new RuntimeException("User " + user + " not found");
        }
        String userPassword = user.getPassword();
        return passwordEncoder.matches(password, userPassword);
    }

}
