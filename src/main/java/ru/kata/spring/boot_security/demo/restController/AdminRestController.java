package ru.kata.spring.boot_security.demo.restController;


import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users1")
public class AdminRestController {
    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> ShowAllUsers() {
        List<User> allUsers = userService.getUsers();
        return allUsers;
    }

    @GetMapping("/{id}")
    public User showUser(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/")
    public User addUser(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @PatchMapping("/{id}")
    public User editUser(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        userService.editUser(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        userService.deleteUserById(id);
        return user;
    }

}
