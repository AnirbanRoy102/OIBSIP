package com.lamra.todoapp.BottomSheetFragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lamra.todoapp.Activities.LogIn;
import com.lamra.todoapp.BackEnd.DatabaseHelper;
import com.lamra.todoapp.Interfaces.DialogCloseListener;
import com.lamra.todoapp.Activities.MainActivity;
import com.lamra.todoapp.R;
import com.lamra.todoapp.Models.taskModel;


public class TaskBottomSheet extends BottomSheetDialogFragment {

    public static final String TAG = "BottomSheet";
    private DatabaseHelper db;
    EditText takeTaskTittle;
    TextView saveTask;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public TaskBottomSheet() {

    }

    public static TaskBottomSheet newInstance(){
        return new TaskBottomSheet();
    }

    public static TaskBottomSheet newInstance(String param1, String param2) {
        TaskBottomSheet fragment = new TaskBottomSheet();
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
        return inflater.inflate(R.layout.fragment_task_bottom_sheet,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        takeTaskTittle = view.findViewById(R.id.takeTaskTittle);
        saveTask = view.findViewById(R.id.saveTask);
        db = new DatabaseHelper(getActivity());

        boolean isUpdate = false;
        Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            takeTaskTittle.setText(task);
            if(task.length()>0){
                saveTask.setTextColor(ContextCompat.getColor(getContext(),R.color.teal_700));
            }
        }
        takeTaskTittle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    saveTask.setEnabled(false);
                    saveTask.setTextColor(Color.GRAY);
                }else{
                    saveTask.setEnabled(true);
                    saveTask.setTextColor(ContextCompat.getColor(getContext(),R.color.teal_700));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        saveTask.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = takeTaskTittle.getText().toString();
            if(finalIsUpdate){
                db.updateTask(bundle.getInt("id"),text);
            }else{
                taskModel task = new taskModel();
                task.setTask(text);
                task.setStatus(0);
                task.setDate(MainActivity.todaysDate.getText().toString());
                task.setUser(LogIn.UserName);
                db.insertTask(task);
            }
            dismiss();
        }
    });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }
}