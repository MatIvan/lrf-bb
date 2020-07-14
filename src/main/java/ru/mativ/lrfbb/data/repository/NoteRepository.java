package ru.mativ.lrfbb.data.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.mativ.lrfbb.data.entity.NoteEntity;

public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {

    @Query("select n from NoteEntity n join fetch n.user where n.user.id = :userId")
    List<NoteEntity> finadAllByUser(@Param("userId") int userId);

    @Query("SELECT n "
            + "FROM NoteEntity n "
            + "WHERE n.user.id = ?1 "
            + "  and n.date = ?2 "
            + "ORDER by id")
    public List<NoteEntity> finadAllForUserByDay(int userId, Date day);
}
