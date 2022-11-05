package mansurpardayev.notes.note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import mansurpardayev.notes.R;
import mansurpardayev.notes.activities.CreateNoteActivity;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    List<Note> notes;
    Context context;
    SimpleDateFormat format, formatHour;
    long currentTime;

    public NoteAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
        format = new SimpleDateFormat("dd/MM/yyyy");
        formatHour = new SimpleDateFormat("hh:mm");
        currentTime = System.currentTimeMillis();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.text.setText(note.getText());

        if (note.getTime() < currentTime - 24 * 60 * 60 * 1000) {
            holder.time.setText(format.format(note.getTime()));

        } else {
            holder.time.setText(formatHour.format(note.getTime()));

        }
        holder.noteItem.setOnClickListener(view -> {
            Intent intent = new Intent(context, CreateNoteActivity.class);
            intent.putExtra("id", note.getId());
            context.startActivity(intent);
        });
    }

    public void resetNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, text, time;
        LinearLayout noteItem;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.noteTitle);
            text = itemView.findViewById(R.id.noteText);
            time = itemView.findViewById(R.id.noteDate);
            noteItem = itemView.findViewById(R.id.noteItem);
        }
    }
}
