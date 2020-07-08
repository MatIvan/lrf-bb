package ru.mativ.lrfbb.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.mativ.lrfbb.data.entity.NoteEntity;

public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {

    @Query("select n from NoteEntity n join fetch n.user where n.user.id = :userId")
    List<NoteEntity> finadAllByUser(@Param("userId") int userId);

}
