package mansurpardayev.notes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import mansurpardayev.notes.note.Note;

@Dao
public interface NoteDaoInterface {
    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);

    @Query("SELECT * FROM Note ORDER BY time DESC")
    List<Note> getAllNotes();

    @Query("SELECT * FROM Note WHERE ID=:id")
    Note getNoteById(long id);

    @Query("SELECT * FROM Note ORDER BY id DESC LIMIT 1")
    Note getNoteByLastItem();
}
