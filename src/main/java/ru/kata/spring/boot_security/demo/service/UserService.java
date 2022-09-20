package ru.kata.spring.boot_security.demo.service;



import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;


public interface UserService extends UserDetailsService {

    void addUser(User user);

    User getUserById(Long id);

    void updateUserById(User user);

    void deleteUserById(Long id);

    List<User> getAllUsers();

    User getUserByName(String name);
}
