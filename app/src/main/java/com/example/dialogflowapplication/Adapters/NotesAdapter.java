package com.example.dialogflowapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dialogflowapplication.Entities.Note;
import com.example.dialogflowapplication.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{
    private List<Note> notes;

    public NotesAdapter(List<Note> notes){
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_note, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.setNote(notes.get(position));
    }

    @Override
    public int getItemViewType(int position){
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle;
        TextView textSubtitle;
        TextView textDateTime;
        public NotesViewHolder(@NonNull View view) {
            super(view);
            textTitle    = (TextView) view.findViewById(R.id.textTitle);
            textSubtitle = (TextView) view.findViewById(R.id.textSubtitle);
            textDateTime = (TextView) view.findViewById(R.id.textDateTime);
        }

        void setNote(Note note){
            textTitle.setText(note.getTitle());
            if(note.getSubtitle().trim().isEmpty()){
                textSubtitle.setVisibility(View.GONE);
            }

            else {
                textSubtitle.setText(note.getTitle());
            }

            textDateTime.setText(note.getDateTime());
        }
    }
}
