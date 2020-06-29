/**
 * @author Ostrovskiy Dmitriy
 * @created 21.06.2020
 * UserDto
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers.dto;

import lombok.Data;
import ru.dinikos.dvd_sharing.backend.entities.Disk;
import ru.dinikos.dvd_sharing.backend.entities.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private Long id;

    @NotNull(message = "Not Null!")
    private String username;

    @NotNull(message = "Not Null!")
    private String firstName;

    @NotEmpty(message = "Not Null!")
    private String password;

    @NotEmpty(message = "Not Null!")
    private String matchingPassword;

    public UserDto(String username, String firstName, String matchingPassword, Set<Role> roles, List<Disk> disks) {
        this.username = username;
        this.firstName = firstName;
        this.matchingPassword = matchingPassword;
        this.roles = roles;
        this.disks = disks;
    }

    private Set<Role> roles;

    private List<Disk> disks;

    @Override
    public String toString() {
        return "UserRepr{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", matchingPassword='" + matchingPassword + '\'' +
                '}';
    }

}
