package ru.mativ.lrfbb.data.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ru.mativ.lrfbb.data.entity.NoteEntity;

public class NotesDto {

    private List<NoteEntity> list;
    private Date dayFilter;

    public NotesDto() {
        list = new ArrayList<NoteEntity>();
    }

    public NotesDto(List<NoteEntity> notes) {
        setList(notes);
    }

    public List<NoteEntity> getList() {
        return list;
    }

    public void setList(List<NoteEntity> list) {
        this.list = new ArrayList<NoteEntity>();
        this.list.addAll(list);
    }

    public Date getDayFilter() {
        return dayFilter;
    }

    public void setDayFilter(Date dayFilter) {
        this.dayFilter = dayFilter;
    }

}
