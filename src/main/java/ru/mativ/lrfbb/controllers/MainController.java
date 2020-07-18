package ru.mativ.lrfbb.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.service.UserService;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        if (principal != null) {
            UserEntity currentUser = userService.findByLogin(principal.getName());
            model.addAttribute("userName", currentUser.getName());
        }else {
            model.addAttribute("userName", false);
        }
        return "index";
    }

}
