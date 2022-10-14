package com.lamra.todoapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamra.todoapp.BackEnd.DatabaseHelper;
import com.lamra.todoapp.BottomSheetFragments.TaskBottomSheet;
import com.lamra.todoapp.Activities.MainActivity;
import com.lamra.todoapp.Models.taskModel;
import com.lamra.todoapp.R;

import java.util.ArrayList;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.taskViewHolder> {

    ArrayList<taskModel> custom_data;
    private DatabaseHelper db;
    private MainActivity activity;

    public taskAdapter(DatabaseHelper db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_blueprint,parent,false);

        return new taskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull taskViewHolder holder, int position) {


        taskModel tModel = custom_data.get(position);

        holder.takeTask.setText(tModel.getTask());
        holder.takeTask.setChecked(giveBoolean(tModel.getStatus()));
        holder.takeTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(tModel.getId(),1);
                }else{
                    db.updateStatus(tModel.getId(), 0 );
                }
            }
        });
    }
    private boolean giveBoolean(int n){
        return n!=0;
    }

    public Context getContext(){
        return activity;
    }

    @Override
    public int getItemCount() {

            return custom_data.size();

    }

    public void setTasks(ArrayList<taskModel> tList) {
        this.custom_data = tList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){
        taskModel tModel = custom_data.get(position);
        db.deleteTask(tModel.getId());
        custom_data.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        taskModel item = custom_data.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task", item.getTask());
        TaskBottomSheet fragment = new TaskBottomSheet();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),fragment.getTag());
    }
    public static class taskViewHolder extends RecyclerView.ViewHolder{
        public CheckBox takeTask;

        public taskViewHolder(@NonNull View itemView) {
            super(itemView);

            takeTask = itemView.findViewById(R.id.taskBox);

        }
    }
}
