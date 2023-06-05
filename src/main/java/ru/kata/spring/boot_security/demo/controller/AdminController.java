package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService service, RoleService roleService) {
        this.userService = service;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String showUsers(Model model) {
        model.addAttribute("usersList", userService.getUsers());
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
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        roles.add(roleService.getRoleByName("ROLE_USER"));
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit_user";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user
            , @RequestParam(value = "new_pass") String newPass
            , @RequestParam(defaultValue = "false") boolean checkbox) {
        Set<Role> roles = new HashSet<>();
        if (checkbox) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        roles.add(roleService.getRoleByName("ROLE_USER"));
        user.setRoles(roles);
        userService.editUser(user, newPass);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/";
    }

}
