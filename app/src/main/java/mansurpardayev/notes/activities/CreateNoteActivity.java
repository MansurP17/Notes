package mansurpardayev.notes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mansurpardayev.notes.R;
import mansurpardayev.notes.database.NoteDatabase;
import mansurpardayev.notes.note.Note;

public class CreateNoteActivity extends AppCompatActivity {

    EditText titleEdit, textEdit;
    FloatingActionButton actionButton;
    String title = "";
    String text = "";
    NoteDatabase database;
    long id;
    ImageView deleteNote;
    Note note = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        titleEdit = findViewById(R.id.newNoteTitle);
        textEdit = findViewById(R.id.newNoteText);
        actionButton = findViewById(R.id.saveNoteBtn);
        deleteNote = findViewById(R.id.deleteNote);

        id = getIntent().getLongExtra("id", -1);
        database = NoteDatabase.getInstance(this);

        setNote();




    }

    private void setNoteData() {
        if (id == -1) return;
        note = database.getNoteInterface().getNoteById(id);
        if (note != null) {
            titleEdit.setText(note.getTitle());
            textEdit.setText(note.getText());
        }
    }

    public void setNote(){
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleEdit.getText().toString();
                text = textEdit.getText().toString();

                if (title.equals("") || text.equals("")) {
                    Toast.makeText(CreateNoteActivity.this, "Title or text is null!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (id == -1) {
                    deleteNote.setVisibility(View.GONE);
                    Note noteView = new Note(title, text);
                    database.getNoteInterface().insert(noteView);
                    note = database.getNoteInterface().getNoteByLastItem();
                    id = note.getId();
                    Toast.makeText(CreateNoteActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                } else if (note != null) {
                    note.setTitle(title);
                    note.setText(text);
                    database.getNoteInterface().update(note);
                    Toast.makeText(CreateNoteActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setNoteData();
    }

    public void backToHome(View view) {
        onBackPressed();
    }

    public void deleteNote(View view) {
        if (note!=null){
            database.getNoteInterface().delete(note);
            onBackPressed();
        }
    }
}