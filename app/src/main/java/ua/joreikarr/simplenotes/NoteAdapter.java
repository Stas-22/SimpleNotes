package ua.joreikarr.simplenotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<String> notes;

    public NoteAdapter(List<String> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        String note = notes.get(position);
        holder.noteText.setText(note);

        holder.itemView.setOnClickListener(v -> {
            EditText editTextField = new EditText(v.getContext());
            editTextField.setText(note);
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Edit Note")
                    .setMessage("Edit your note:")
                    .setView(editTextField)
                    .setPositiveButton("Save", (dialog, which) -> {
                        String editedNote = editTextField.getText().toString();
                        notes.set(position, editedNote);
                        notifyItemChanged(position);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        notes.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, notes.size());
                        dialog.dismiss();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteText;

        NoteViewHolder(View itemView) {
            super(itemView);
            noteText = itemView.findViewById(android.R.id.text1);
        }
    }
}

