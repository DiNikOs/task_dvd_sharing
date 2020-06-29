/**
 * @author Ostrovskiy Dmitriy
 * @created 20.06.2020
 * UserController
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.dinikos.dvd_sharing.backend.repository.RoleRepository;
import ru.dinikos.dvd_sharing.backend.services.UserServiceInterface;

@Controller
public class UserController {
    private final RoleRepository roleRepository;
    private final UserServiceInterface userService;

    @Autowired
    public UserController(RoleRepository roleRepository,
                          UserServiceInterface userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

}
