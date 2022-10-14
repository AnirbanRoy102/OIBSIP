package com.lamra.todoapp.DialogFragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lamra.todoapp.Activities.LogIn;
import com.lamra.todoapp.BackEnd.DatabaseScheduleHelper;
import com.lamra.todoapp.Interfaces.DialogCloseListenerSchedule;
import com.lamra.todoapp.Activities.MainActivity;
import com.lamra.todoapp.Models.scheduleModel;
import com.lamra.todoapp.R;

import nl.joery.timerangepicker.TimeRangePicker;


public class scheduleBottomSheet extends DialogFragment {


    TimeRangePicker picker;
    public static final String TAG = "scheduleBottomSheet";
    private DatabaseScheduleHelper db;
    boolean isActive_scheduleTask = false;
    boolean isActive_scheduleMeeting = false;
    boolean isActive_scheduleEvent = false;
    TextView scheduleTask,scheduleMeeting,scheduleEvent,saveSchedule,showStartTime,showEndTime;
    EditText scheduleInfo;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public scheduleBottomSheet() {

    }
    public static scheduleBottomSheet newInstance(){
        return new scheduleBottomSheet();
    }


    public static scheduleBottomSheet newInstance(String param1, String param2) {
        scheduleBottomSheet fragment = new scheduleBottomSheet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule_bottom_sheet, container, false);

        picker = view.findViewById(R.id.picker);
        showStartTime = view.findViewById(R.id.showStartTime);
        showEndTime = view.findViewById(R.id.showEndTime);

        showStartTime.setText("1:00");
        showEndTime.setText("6:00");

        picker.setOnTimeChangeListener(new TimeRangePicker.OnTimeChangeListener() {
            @Override
            public void onStartTimeChange(@NonNull TimeRangePicker.Time startTime) {
                showStartTime.setText(startTime.getHour()+":"+ startTime.getMinute());

            }

            @Override
            public void onEndTimeChange(@NonNull TimeRangePicker.Time endTime) {
                Log.d("TimeRangePicker", "End time: " + endTime.getHour());
                showEndTime.setText(endTime.getHour()+":"+endTime.getMinute());
            }

            @Override
            public void onDurationChange(@NonNull TimeRangePicker.TimeDuration timeDuration) {
                Log.d("TimeRangePicker", "Duration: " + timeDuration.getHour());
            }
        });





        picker.setOnDragChangeListener(new TimeRangePicker.OnDragChangeListener() {
            @Override
            public boolean onDragStart(@NonNull TimeRangePicker.Thumb thumb) {

                return true;
            }

            @Override
            public void onDragStop(@NonNull TimeRangePicker.Thumb thumb) {

            }
        });


        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scheduleTask = view.findViewById(R.id.takeScheduleTask);
        scheduleMeeting = view.findViewById(R.id.takeScheduleMeeting);
        scheduleEvent = view.findViewById(R.id.takeScheduleEvent);
        scheduleInfo = view.findViewById(R.id.takeScheduleInfo);
        saveSchedule = view.findViewById(R.id.saveSchedule);
        db = new DatabaseScheduleHelper(getActivity());

        boolean isUpdate = false;
        Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String info = bundle.getString("scheduleInfo");
            int  status = bundle.getInt("scheduleStatus");
            scheduleInfo.setText(info);
            if(status == 1 ||status == 2 || status == 3){
                saveSchedule.setTextColor(ContextCompat.getColor(getContext(),R.color.teal_700));
            }
        }
        ;
        scheduleTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isActive_scheduleTask){
                    scheduleTask.setBackgroundResource(R.drawable.task_box);
                    isActive_scheduleTask = true;
                    isActive_scheduleMeeting = false;
                    isActive_scheduleEvent = false;
                }
                scheduleMeeting.setBackgroundResource(R.drawable.in_active_box);
                scheduleEvent.setBackgroundResource(R.drawable.in_active_box);
            }
        });
        scheduleMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isActive_scheduleMeeting){
                    scheduleMeeting.setBackgroundResource(R.drawable.meeting_box);
                    isActive_scheduleMeeting = true;
                    isActive_scheduleTask = false;
                    isActive_scheduleEvent = false;
                }
                scheduleTask.setBackgroundResource(R.drawable.in_active_box);
                scheduleEvent.setBackgroundResource(R.drawable.in_active_box);
            }
        });
        scheduleEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isActive_scheduleEvent){
                    scheduleEvent.setBackgroundResource(R.drawable.event_box);
                    isActive_scheduleEvent = true;
                    isActive_scheduleTask = false;
                    isActive_scheduleMeeting = false;
                }
                scheduleTask.setBackgroundResource(R.drawable.in_active_box);
                scheduleMeeting.setBackgroundResource(R.drawable.in_active_box);
            }
        });

        boolean finalIsUpdate = isUpdate;
        saveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isActive_scheduleTask && !isActive_scheduleMeeting && !isActive_scheduleEvent) {
                    Toast.makeText(getContext(), "Select Type", Toast.LENGTH_SHORT).show();
                } else {
                    int status = 0;
                    if (isActive_scheduleTask) {
                        status = 1;
                    } else if (isActive_scheduleMeeting) {
                        status = 2;
                    } else if (isActive_scheduleEvent) {
                        status = 3;
                    }
                    if (finalIsUpdate) {
                        db.updateStatus(bundle.getInt("scheduleStatus"), status);
                    } else {
                        scheduleModel schedule = new scheduleModel();
                        schedule.setStatus(status);
                        schedule.setAddtionalInfo(scheduleInfo.getText().toString());
                        schedule.setDate(MainActivity.todaysDate.getText().toString());
                        schedule.setUser(LogIn.UserName);
                        schedule.setTime(showStartTime.getText().toString()+" - "+showEndTime.getText().toString());
                        db.insertSchedule(schedule);
                    }
                    dismiss();
                }
            }
        });

        scheduleInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>580){
                    Toast.makeText(getContext(), "Limit Reached", Toast.LENGTH_SHORT).show();
                    scheduleInfo.setText(scheduleInfo.getText().toString().substring(0,scheduleInfo.getText().toString().length()-1));
                    scheduleInfo.setSelection(scheduleInfo.getText().toString().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListenerSchedule){
            ((DialogCloseListenerSchedule)activity).handleScheduleDialogClose(dialog);
        }
    }

}