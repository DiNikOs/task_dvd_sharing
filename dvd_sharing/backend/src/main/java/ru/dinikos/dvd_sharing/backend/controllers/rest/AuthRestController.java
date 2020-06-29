/**
 * @author Ostrovskiy Dmitriy
 * @created 26.06.2020
 * AuthRestController
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.dinikos.dvd_sharing.backend.controllers.dto.AuthDto;
import ru.dinikos.dvd_sharing.backend.controllers.dto.AuthRequest;
import ru.dinikos.dvd_sharing.backend.controllers.dto.AuthResponse;
import ru.dinikos.dvd_sharing.backend.entities.User;
import ru.dinikos.dvd_sharing.backend.exception.ValidationErrorBuilder;
import ru.dinikos.dvd_sharing.backend.services.AuthService;
import ru.dinikos.dvd_sharing.backend.controllers.dto.SystemUser;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v0")
@CrossOrigin(origins = "*")
public class AuthRestController {

    @Autowired
    private AuthService authService;

    //TODO Метод регитрации пользователя
    /**
     * Метод регитрации пользователя
     */
    @PostMapping(value = "/register" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUser(@RequestBody @Valid AuthDto authDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        SystemUser systemUser = new SystemUser(authDto.getUsername(), authDto.getFirstName(), authDto.getPassword());
        User user = authService.saveUser(systemUser);
        return user == null ? new ResponseEntity<>("User with username is already exist!", HttpStatus.NOT_ACCEPTABLE)
                :new ResponseEntity<User>(user, HttpStatus.OK);
    }

    //TODO Метод авторизации пользователя ОКНО1
    /**
     * Метод авторизации пользователя
     */
    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> auth(@RequestBody @Valid AuthRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        AuthResponse response = authService.getAuthResponse(request);
        return response == null ? new ResponseEntity<>("User not found!",HttpStatus.NOT_FOUND)
                :new ResponseEntity<AuthResponse>(response, HttpStatus.OK);
    }

    //TODO Метод выхода из состояния авторизованного пользователя (логаут)
    /**
     * Метод выхода из состояния авторизованного пользователя (логаут)
     */

    @DeleteMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(@RequestBody AuthResponse response) {
        Boolean delete = authService.deliteAuth(response.getUserId(), response.getToken());
        return delete == null ? new ResponseEntity<>("Not successful!", HttpStatus.NOT_ACCEPTABLE)
                :new ResponseEntity<>("Logout completed!",HttpStatus.OK);
    }

}
