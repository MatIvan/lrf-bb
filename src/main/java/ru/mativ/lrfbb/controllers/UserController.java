package ru.mativ.lrfbb.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.mativ.lrfbb.data.dto.UserDto;
import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.service.RoleService;
import ru.mativ.lrfbb.data.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @GetMapping("/allUsers")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllDto());
        model.addAttribute("allRoles", roleService.getAllDto());
        return "manager/allUsers";
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam(value = "id") Integer userId, Model model) {
        UserEntity user = userService.findById(userId);
        //TODO process error
        model.addAttribute("user", UserDto.make(user));
        model.addAttribute("allRoles", roleService.getAllDto());
        return "manager/editUserPage";
    }

    @PostMapping("/editUser")
    public String addUser(@ModelAttribute("user") @Valid UserDto user, BindingResult bindingResult, Model model) {
        final String view = "manager/editUserPage";
        if (bindingResult.hasErrors()) {
            return view;
        }
        //TODO need validate ID and PASS
        try {
            model.addAttribute("user", userService.save(user));
            model.addAttribute("allRoles", roleService.getAllDto());
            model.addAttribute("errorMessage", "User saved.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error edit user.");
            model.addAttribute("errorDescription", e.getMessage());
            return view;
        }

        return view;
    }

}
