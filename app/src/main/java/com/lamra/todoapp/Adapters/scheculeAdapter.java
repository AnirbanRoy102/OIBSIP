package com.lamra.todoapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamra.todoapp.BackEnd.DatabaseScheduleHelper;
import com.lamra.todoapp.DialogFragments.scheduleBottomSheet;
import com.lamra.todoapp.Activities.MainActivity;
import com.lamra.todoapp.Models.scheduleModel;
import com.lamra.todoapp.R;

import java.util.ArrayList;

public class scheculeAdapter extends RecyclerView.Adapter<scheculeAdapter.scheduleViewHolder> {

    ArrayList<scheduleModel> custom_data;
    private DatabaseScheduleHelper db;
    private MainActivity activity;

    public scheculeAdapter(DatabaseScheduleHelper db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public scheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedukle_blueprint, parent, false);

        return new scheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull scheduleViewHolder holder, int position) {
        scheduleModel sModel = custom_data.get(position);
        holder.scheduleBoxInfo.setText(sModel.getAddtionalInfo());
        holder.scheduleBoxTime.setText(sModel.getTime());
        if(sModel.getStatus() == 1){
            holder.scheduleBox.setBackgroundResource(R.drawable.task_box);
        }else if(sModel.getStatus() == 2){
            holder.scheduleBox.setBackgroundResource(R.drawable.meeting_box);
        }else if(sModel.getStatus() == 3){
            holder.scheduleBox.setBackgroundResource(R.drawable.event_box);
        }
    }

    @Override
    public int getItemCount() {
        return custom_data.size();
    }

    public Context getContext(){
        return activity;
    }

    public void setschedules(ArrayList<scheduleModel> sList) {
        this.custom_data = sList;
        notifyDataSetChanged();
    }

    public void deleteSchedule(int position){
        scheduleModel sModel = custom_data.get(position);
        db.deleteSchedule(sModel.getId());
        custom_data.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        scheduleModel item = custom_data.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("scheduleInfo", item.getAddtionalInfo());
        bundle.putInt("scheduleStatus", item.getStatus());
        bundle.putString("scheduleTime", item.getTime());
        scheduleBottomSheet fragment = new scheduleBottomSheet();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),fragment.getTag());
    }

    public static class scheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView scheduleBoxTime,scheduleBoxInfo;
        public LinearLayout scheduleBox;

        public scheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            scheduleBox  = itemView.findViewById(R.id.scheduleBox);
            scheduleBoxTime = itemView.findViewById(R.id.scheduleBoxTime);
            scheduleBoxInfo = itemView.findViewById(R.id.scheduleBoxInfo);
        }
    }
}
