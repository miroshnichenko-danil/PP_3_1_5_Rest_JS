package ru.kata.spring.boot_security.demo.restController;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {
    private final UserService userService;

    public RestApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> ShowAllUsers() {
        List<User> allUsers = userService.getUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> showUser(@PathVariable("id") long id) {
        return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/admin/{id}")
    public ResponseEntity<User> editUser(@RequestBody User user
            , @PathVariable("id") long id) {
        userService.editUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
