package mansurpardayev.notes.note;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "Note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public long id;
    String title;
    String text;
    long time;

    public Note(long id, String title, String text, long time) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.time = time;
    }

    @Ignore
    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        this.time = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }
}
