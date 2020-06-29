/**
 * @author Ostrovskiy Dmitriy
 * @created 21.06.2020
 * MainController
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dinikos.dvd_sharing.backend.services.DiskService;
import ru.dinikos.dvd_sharing.backend.services.UserService;
import ru.dinikos.dvd_sharing.backend.controllers.dto.SystemUser;

@Controller
public class MainController {

    private UserService userService;
    private DiskService diskService;

    @Autowired
    public MainController(UserService userService, DiskService diskService) {
        this.userService = userService;
        this.diskService = diskService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("systemUser", new SystemUser());
        return "register";
    }

}
