package com.tavleen.mynotesapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("Select * FROM note")
    List<Note> fetchNotes();

    @Insert
    void createNote(Note note);

    @Delete
    void deleteNote(Note note);
}
