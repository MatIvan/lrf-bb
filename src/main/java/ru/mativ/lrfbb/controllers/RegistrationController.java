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

@Controller
public class RegistrationController {

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
        return "users/registration";
    }

    @PostMapping("registration")
    public String addUser(@ModelAttribute("user") @Valid UserDto user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/registration";
        }

        // TODO Save user to db
        //        if (!userService.saveUser(user)){
        //            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
        //            return "users/registration";
        //        }
        
        user.setPassword("***"); //hide password
        model.addAttribute("user", user);
        return "users/reg-success";
    }
}
