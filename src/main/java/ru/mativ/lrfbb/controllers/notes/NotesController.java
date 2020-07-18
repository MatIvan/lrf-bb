package ru.mativ.lrfbb.controllers.notes;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.mativ.lrfbb.data.dto.NotesDto;
import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.service.NoteService;
import ru.mativ.lrfbb.data.service.UserService;

@Controller
public class NotesController {

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @GetMapping("/allnotes")
    public String getAllNotes(Model model, Principal principal) {

        UserEntity currentUser = userService.findByLogin(principal.getName());
        model.addAttribute("notesDto", new NotesDto(noteService.getAllForUser(currentUser)));
        model.addAttribute("user", currentUser);

        return "notes/allNotesPage";
    }

}
