package ru.kata.spring.boot_security.demo.initialization;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class DbInit {
    private final UserService userService;
    private final RoleService roleService;

    public DbInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void defaultUsers() {
        Role adminRole = roleService.findRoleByName("ROLE_ADMIN");
        Role userRole = roleService.findRoleByName("ROLE_USER");
        Set<Role> userRoles = new HashSet<>();
        Set<Role> adminRoles = new HashSet<>();
        userRoles.add(adminRole);
        adminRoles.add(userRole);
        User user = new User("user", "user", "user@user.ru", 3, userRoles, "user");
        User admin = new User("admin1", "admin1", "admin@admin.ru", 2, adminRoles, "admin");
        userService.addUser(user);
        userService.addUser(admin);
    }
}
