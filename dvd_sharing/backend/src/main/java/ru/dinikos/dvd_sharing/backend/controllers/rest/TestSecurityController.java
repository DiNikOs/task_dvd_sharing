package ru.dinikos.dvd_sharing.backend.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0")
public class TestSecurityController {

    @GetMapping("admin/get")
    public String getAdmin() {
        return "Hi admin";
    }

    @GetMapping("users/get")
    public String getUser() {
        return "Hi user";
    }
}
