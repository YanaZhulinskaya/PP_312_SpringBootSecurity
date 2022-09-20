package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller

public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/user")
    public String UserPage(Principal principal, Model model) {
        model.addAttribute(userService.getUserByName(principal.getName()));
        return "user";
    }
}
