package ru.mativ.lrfbb.controllers;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ErrorService {

    public String showError(Model model, String error, String message) {
        model.addAttribute("timestamp", new java.util.Date(System.currentTimeMillis()));
        model.addAttribute("path", "");
        model.addAttribute("error", error);
        model.addAttribute("status", "");
        model.addAttribute("message", message);
        return "error";
    }

}
