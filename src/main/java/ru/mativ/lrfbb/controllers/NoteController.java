package ru.mativ.lrfbb.controllers;

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

import ru.mativ.lrfbb.data.dto.NotesDto;
import ru.mativ.lrfbb.data.entity.NoteEntity;
import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.service.NoteService;
import ru.mativ.lrfbb.data.service.UserService;

@Controller
public class NoteController {

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

    @GetMapping("/note")
    public String getNote(@RequestParam(value = "id") Integer noteId, Model model, Principal principal) {
        NoteEntity note = noteService.getById(noteId);
        UserEntity currentUser = userService.findByLogin(principal.getName());
        UserEntity noteOwner = note.getUser();

        if (noteOwner.getId() != currentUser.getId()) {
            // TODO process error
        }

        model.addAttribute("note", note);
        model.addAttribute("user", noteOwner);
        model.addAttribute("messages", new ArrayList<String>());

        return "notes/editNote";
    }

    @PostMapping("/note")
    public String editNote(@ModelAttribute("note") NoteEntity note, Model model, Principal principal) {
        UserEntity currentUser = userService.findByLogin(principal.getName());
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

    @GetMapping("/day/now")
    public String dayNotesNow(Model model, Principal principal) {
        return dayNotesFilter(new Date(System.currentTimeMillis()), model, principal);
    }

    @GetMapping("/day/filter")
    public String dayNotesFilter(@RequestParam(value = "date") Date filterDate, Model model, Principal principal) {
        UserEntity currentUser = userService.findByLogin(principal.getName());

        NotesDto notesDto = new NotesDto(noteService.getAllForUserByDay(currentUser, filterDate));
        notesDto.setDayFilter(filterDate);

        model.addAttribute("notesDto", notesDto);
        model.addAttribute("user", currentUser);
        model.addAttribute("messages", new ArrayList<String>());
        return "notes/dayNotes";
    }

    @PostMapping("/saveDayNotes")
    public String saveDayNotes(@ModelAttribute NotesDto notesDto, Model model, Principal principal) {
        UserEntity currentUser = userService.findByLogin(principal.getName());
        Date dayFilter = notesDto.getDayFilter();

        if (dayFilter == null) {
            return "redirect:/day/now";
        }

        notesDto.getList().forEach((note) -> {
            note.setUser(currentUser);
            note.setDate(dayFilter);
        });

        noteService.saveAll(notesDto.getList());
        return dayNotesFilter(dayFilter, model, principal);
    }
}
