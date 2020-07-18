package ru.mativ.lrfbb.data.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.mativ.lrfbb.data.entity.NoteEntity;
import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.repository.NoteRepository;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public NoteEntity save(NoteEntity noteEntity) {
        return noteRepository.save(noteEntity);
    }

    public List<NoteEntity> getAllForUser(UserEntity user) {
        return noteRepository.finadAllByUser(user.getId());
    }

    public NoteEntity getById(Integer noteId) {
        Optional<NoteEntity> note = noteRepository.findById(noteId);
        if (note.isPresent()) {
            return note.get();
        }
        return null;
    }

    public List<NoteEntity> getAllForUserByDay(UserEntity user, Date day) {
        return noteRepository.finadAllForUserByDay(user.getId(), day);
    }

    public List<NoteEntity> saveAll(List<NoteEntity> notes) {
        return noteRepository.saveAll(notes);
    }
}
