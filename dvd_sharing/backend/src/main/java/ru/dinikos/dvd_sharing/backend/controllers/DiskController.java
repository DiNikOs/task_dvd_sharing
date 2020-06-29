/**
 * @author Ostrovskiy Dmitriy
 * @created 12.06.2020
 * DataController
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dinikos.dvd_sharing.backend.controllers.dto.DiskDto;
import ru.dinikos.dvd_sharing.backend.entities.User;
import ru.dinikos.dvd_sharing.backend.services.DiskService;
import ru.dinikos.dvd_sharing.backend.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/disks")
public class DiskController {

    private DiskService diskService;
    private UserService userService;

    @Autowired
    public DiskController(DiskService diskService, UserService userService) {
        this.diskService = diskService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getPageAllDisks(Model model, @PathVariable(value = "id", required = false) Long id) {
        model.addAttribute("disks", diskService.findAllDisk());
        return "disks";
    }

    @GetMapping("/taken")
    public String getPageTaken(Model model, Principal principal) {
        model.addAttribute("disks", diskService.findTakenDisksByUserName(principal.getName()));
        return "taken";
    }

    @GetMapping("/given")
    public String getPageGiven(Model model,  Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("disks", diskService.findGivenDisksByUser(user));
        return "given";
    }

    @GetMapping("/user_disks")
    public String getPageUserDisks(Model model, Principal principal) {
        model.addAttribute("disks", diskService.findDiskByUserName(principal.getName()));
        return "user_disks";
    }

    @PostMapping("/save")
    public String addDisk(@ModelAttribute("disk") DiskDto diskDto, Model model) {
        diskService.save(diskDto);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteData(@PathVariable("id") Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        diskService.deleteUserDisk(id, user);
        return "redirect:/";
    }
}
