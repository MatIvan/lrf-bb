package ru.mativ.lrfbb.data.service;

import java.util.List;

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
        return noteRepository.finadAllByUserEntity(user);
    }
}
