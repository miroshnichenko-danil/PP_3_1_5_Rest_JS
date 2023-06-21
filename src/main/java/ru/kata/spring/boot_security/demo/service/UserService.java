package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    void deleteUserById(long id);

    void addUser(User user);

    void editUser(User user);
    User getUserById(long id);
    User getUserByEmail(String email);
}
