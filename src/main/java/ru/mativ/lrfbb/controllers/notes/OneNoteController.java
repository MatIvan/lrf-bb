package ru.mativ.lrfbb.controllers.notes;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.mativ.lrfbb.controllers.ErrorService;
import ru.mativ.lrfbb.data.entity.NoteEntity;
import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.service.NoteService;
import ru.mativ.lrfbb.data.service.UserService;

@Controller
public class OneNoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @Autowired
    ErrorService errorService;

    @GetMapping("/note")
    public String getNote(@RequestParam(value = "id") Integer noteId, Model model, Principal principal) {
        NoteEntity note = noteService.getById(noteId);
        UserEntity currentUser = userService.findByLogin(principal.getName());
        UserEntity noteOwner = note.getUser();

        if (noteOwner.getId() != currentUser.getId()) {
            return errorService.showError(model, "Access deny.", "This note (id=" + noteId + ") for other user.");
        }

        model.addAttribute("note", note);
        model.addAttribute("user", noteOwner);
        model.addAttribute("messages", new ArrayList<String>());

        return "notes/editNote";
    }

    @PostMapping("/note")
    public String editNote(@ModelAttribute("note") NoteEntity note, Model model, Principal principal) {
        NoteEntity oldNote = noteService.getById(note.getId());
        UserEntity currentUser = userService.findByLogin(principal.getName());

        if (oldNote.getUser().getId() != currentUser.getId()) {
            return errorService.showError(model, "Access deny.", "This note (id=" + note.getId() + ") for other user.");
        }

        note.setUser(currentUser);
        NoteEntity savedNote = noteService.save(note);
        model.addAttribute("note", savedNote);
        model.addAttribute("user", currentUser);
        model.addAttribute("messages", Arrays.asList("Note saved."));

        return "notes/editNote";
    }

    @GetMapping("/addNote")
    public String editNote(Model model, Principal principal) {
        UserEntity currentUser = userService.findByLogin(principal.getName());

        NoteEntity newNote = new NoteEntity();
        newNote.setUser(currentUser);
        newNote.setDate(new Date(System.currentTimeMillis()));

        model.addAttribute("note", newNote);
        model.addAttribute("user", currentUser);
        model.addAttribute("messages", new ArrayList<String>());

        return "notes/editNote";
    }

}
