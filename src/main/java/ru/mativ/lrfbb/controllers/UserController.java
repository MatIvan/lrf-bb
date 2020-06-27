package ru.mativ.lrfbb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

import ru.mativ.lrfbb.data.service.RoleService;
import ru.mativ.lrfbb.data.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @GetMapping("/users")
    public String showAllUsers(WebRequest request, Model model) {
        model.addAttribute("users", userService.getAllDto());
        model.addAttribute("roles", roleService.getAllDto());
        return "manager/users";
    }

}
