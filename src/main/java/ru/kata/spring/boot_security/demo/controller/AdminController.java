package ru.kata.spring.boot_security.demo.controller;

import org.slf4j.helpers.CheckReturnValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;

    public AdminController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String showUsers(Model model) {
        model.addAttribute("usersList", service.getUsers());
        return "all_users";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new_user";
    }

    @PostMapping("/")
    public String addUser(@ModelAttribute("user") User user
            , @RequestParam(defaultValue = "false") boolean checkbox) {
        Set<Role> roles = new HashSet<>();
        if (checkbox) {
            roles.add(new Role(1L, "ROLE_ADMIN"));
        }
        roles.add(new Role(2L, "ROLE_USER"));
        user.setRoles(roles);
        service.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", service.getUserById(id));
        return "edit_user";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user
            , @RequestParam(value = "new_pass") String newPass
            , @RequestParam(defaultValue = "false") boolean checkbox) {
        Set<Role> roles = new HashSet<>();
        if (checkbox) {
            roles.add(new Role(1L, "ROLE_ADMIN"));
        }
        roles.add(new Role(2L, "ROLE_USER"));
        user.setRoles(roles);
        service.editUser(user, newPass);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        service.deleteUserById(id);
        return "redirect:/admin/";
    }

}
