package ru.mativ.lrfbb.controllers.notes;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.mativ.lrfbb.controllers.ErrorService;
import ru.mativ.lrfbb.data.dto.NotesDto;
import ru.mativ.lrfbb.data.entity.NoteEntity;
import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.service.NoteService;
import ru.mativ.lrfbb.data.service.UserService;

@Controller
public class DayNotesController {

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @Autowired
    ErrorService errorService;

    @GetMapping("/day/now")
    public String dayNow(Model model, Principal principal) {
        return dayFilter(new Date(System.currentTimeMillis()), model, principal);
    }

    @GetMapping("/day/filter")
    public String dayFilter(@RequestParam(value = "date") Date filterDate, Model model, Principal principal) {
        UserEntity currentUser = userService.findByLogin(principal.getName());

        NotesDto notesDto = new NotesDto(noteService.getAllForUserByDay(currentUser, filterDate));
        notesDto.setDayFilter(filterDate);

        model.addAttribute("notesDto", notesDto);
        model.addAttribute("user", currentUser);
        model.addAttribute("messages", new ArrayList<String>());
        return "notes/dayNotes";
    }

    @PostMapping("/day/save")
    public String save(@ModelAttribute NotesDto notesDto, Model model, Principal principal) {
        UserEntity currentUser = userService.findByLogin(principal.getName());
        Date dayFilter = notesDto.getDayFilter();

        if (dayFilter == null) {
            return errorService.showError(model, "Filter error.", "Day filter is empty.");
        }

        notesDto.getList().forEach((note) -> {
            note.setUser(currentUser);
            note.setDate(dayFilter);
        });

        noteService.saveAll(notesDto.getList());
        return "redirect:/day/filter?date=" + dayFilter.toString();
    }

    @GetMapping("/day/add")
    public String add(@RequestParam(value = "date") Date dayFilter, Model model, Principal principal) {
        UserEntity currentUser = userService.findByLogin(principal.getName());
        NoteEntity newNote = new NoteEntity();
        newNote.setUser(currentUser);
        newNote.setDate(dayFilter);
        noteService.save(newNote);
        return "redirect:/day/filter?date=" + dayFilter.toString();
    }
}
