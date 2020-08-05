package ru.mativ.lrfbb.controllers.reports;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.service.ReportService;
import ru.mativ.lrfbb.data.service.UserService;

@Controller
public class ReportsController {

    @Autowired
    ReportService reportService;

    @Autowired
    UserService userService;
    
    @GetMapping("/report/all")
    public String getAllReports(Model model, Principal principal) {

        UserEntity currentUser = userService.findByLogin(principal.getName());
        //model.addAttribute("user", currentUser);
        
        reportService.getAllForUser(currentUser);
        System.out.println("### REPORTS: " + reportService.getAllForUser(currentUser));
        
        //model.addAttribute("notesDto", new NotesDto(noteService.getAllForUser(currentUser)));

        return "reorts/allReportsPage";
    }
}
