package ru.mativ.lrfbb.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import ru.mativ.lrfbb.data.dto.UserDto;
import ru.mativ.lrfbb.data.service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("userValidator")
    private Validator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @GetMapping("registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration/registration";
    }

    @PostMapping("registration")
    public String addUser(@ModelAttribute("user") @Valid UserDto user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration/registration";
        }

        try {
            user = UserDto.make(userService.newUser(user));
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error user creation.");
            model.addAttribute("errorDescription", e.getMessage());
            e.printStackTrace();
            return "registration/reg-error";
        }

        user.setPassword("***"); //hide password
        model.addAttribute("user", user);
        return "registration/reg-success";
    }

}
