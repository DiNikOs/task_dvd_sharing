/**
 * @author Ostrovskiy Dmitriy
 * @created 26.06.2020
 * AuthDto
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class AuthDto {
    @NotNull(message = "Not Null!")
    @Size(min = 4, max = 100, message = "Имя пользователя от 4 до 100 символов")
    private String username;

    @NotNull(message = "Not Null!")
    @Size(min = 4, max = 30, message = "Too short <4!")
    //    @Pattern(regexp = "(?=^.{4,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z])", message = "Must be a-Z,0-9,_'^&/+! and <4")
    private String password;

    @NotNull(message = "Not Null!")
    @Size(min = 4, max = 30, message = "Too short <4!")
    //    @Pattern(regexp = "(?=^.{4,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z])", message = "Must be a-Z,0-9,_'^&/+! and <4")
    private String matchingPassword;

    @NotEmpty(message = "Not Empty!")
    private String firstName;

    @AssertTrue(message="The password fields must match")
    private boolean isValid() {
        return this.password.equals(this.matchingPassword);
    }

}
