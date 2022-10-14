package com.lamra.todoapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamra.todoapp.BackEnd.DatabaseNoteHelper;
import com.lamra.todoapp.BottomSheetFragments.NotesBottomSheet;
import com.lamra.todoapp.Activities.MainActivity;
import com.lamra.todoapp.Models.notesModel;
import com.lamra.todoapp.R;

import java.util.ArrayList;

public class notesAdapter extends RecyclerView.Adapter<notesAdapter.NotesViewHolder> {

    ArrayList<notesModel> custom_data;
    private DatabaseNoteHelper db;
    private MainActivity activity;

    public notesAdapter(DatabaseNoteHelper db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_blueprint,parent,false);

        return new notesAdapter.NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        notesModel nModel = custom_data.get(position);

        holder.takeNote.setText(nModel.getNote());

    }
    public Context getContext(){
        return activity;
    }
    @Override
    public int getItemCount() {
        return custom_data.size();
    }

    public void setNotes(ArrayList<notesModel> nList) {
        this.custom_data = nList;
        notifyDataSetChanged();
    }

    public void deleteNote(int position){
        notesModel nModel = custom_data.get(position);
        db.deleteNote(nModel.getId());
        custom_data.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        notesModel item = custom_data.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("note", item.getNote());
        NotesBottomSheet fragment = new NotesBottomSheet();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),fragment.getTag());
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder{
        public EditText takeNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            takeNote = itemView.findViewById(R.id.noteBox);

        }
    }
}
