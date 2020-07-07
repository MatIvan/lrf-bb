package ru.mativ.lrfbb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.mativ.lrfbb.data.service.NoteService;

@Controller
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping("/notes")
    public String getAllNotes(Model model) {
        
        return "notes/allNotesList";
    }
}
