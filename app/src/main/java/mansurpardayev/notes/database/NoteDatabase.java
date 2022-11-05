package mansurpardayev.notes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import mansurpardayev.notes.note.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDaoInterface getNoteInterface();

    public static volatile NoteDatabase noteDatabase;

    public static NoteDatabase getInstance(Context context) {
        if (noteDatabase == null) {
            synchronized (NoteDatabase.class) {
                if (noteDatabase == null) {
                    noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, "note_db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return noteDatabase;
    }
}
