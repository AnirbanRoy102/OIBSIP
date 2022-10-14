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
import com.lamra.todoapp.BackEnd.DatabaseNoteHelper;
import com.lamra.todoapp.Interfaces.DialogCloseListenerNote;
import com.lamra.todoapp.Activities.MainActivity;
import com.lamra.todoapp.Models.notesModel;
import com.lamra.todoapp.R;


public class NotesBottomSheet extends BottomSheetDialogFragment {

    EditText takeNote;
    TextView saveNote;
    private DatabaseNoteHelper db;
    public static final String TAG = "NotesBottomSheet";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NotesBottomSheet() {

    }
    public static NotesBottomSheet newInstance(){
        return new NotesBottomSheet();
    }


    public static NotesBottomSheet newInstance(String param1, String param2) {
        NotesBottomSheet fragment = new NotesBottomSheet();
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

        return inflater.inflate(R.layout.fragment_notes_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        takeNote = view.findViewById(R.id.takeNote);
        saveNote = view.findViewById(R.id.saveNote);
        db = new DatabaseNoteHelper(getActivity());


        boolean isUpdate = false;
        Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String note = bundle.getString("note");
            takeNote.setText(note);
            if(note.length()>0){
                saveNote.setTextColor(ContextCompat.getColor(getContext(),R.color.teal_700));
            }
        }
        takeNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    saveNote.setEnabled(false);
                    saveNote.setTextColor(Color.GRAY);
                }else{
                    saveNote.setEnabled(true);
                    saveNote.setTextColor(ContextCompat.getColor(getContext(),R.color.teal_700));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = takeNote.getText().toString();
                if(finalIsUpdate){
                    db.updateNote(bundle.getInt("id"),text);
                }else{
                    notesModel note = new notesModel();
                    note.setNote(text);
                    note.setDate(MainActivity.todaysDate.getText().toString());
                    note.setUser(LogIn.UserName);
                    db.insertNote(note);
                }
                dismiss();
            }
        });

    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListenerNote){
            ((DialogCloseListenerNote)activity).handleNoteDialogClose(dialog);
        }
    }

}