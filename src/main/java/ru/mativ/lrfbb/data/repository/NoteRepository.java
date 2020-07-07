package ru.mativ.lrfbb.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mativ.lrfbb.data.entity.NoteEntity;
import ru.mativ.lrfbb.data.entity.UserEntity;

public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {

    List<NoteEntity> finadAllByUserEntity(UserEntity userEntity);

}
