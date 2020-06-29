/**
 * @author Ostrovskiy Dmitriy
 * @created 21.06.2020
 * SystemUser validation
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dinikos.dvd_sharing.backend.entities.Disk;
import ru.dinikos.dvd_sharing.backend.entities.Role;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class SystemUser {

    private Long id;

    private String username;

    private String password;

    private String firstName;

    public SystemUser(String username, String firstName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.password = password;
    }

    private Set<Role> roles;
    private List<Disk> disks;

    @Override
    public String toString() {
        return "SystemUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
