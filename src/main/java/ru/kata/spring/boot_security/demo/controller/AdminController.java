package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String showUsers(Model model, @ModelAttribute("newUser") User newUser) {
        model.addAttribute("usersList", userService.getUsers());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User curUser = userService.getUserByEmail(auth.getName());
        model.addAttribute("curUser", curUser);
        model.addAttribute("rolesList", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("/")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @PatchMapping("/")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.editUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/";
    }

}
