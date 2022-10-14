package com.lamra.todoapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lamra.todoapp.Adapters.notesAdapter;
import com.lamra.todoapp.Adapters.scheculeAdapter;
import com.lamra.todoapp.Adapters.taskAdapter;
import com.lamra.todoapp.BackEnd.DatabaseHelper;
import com.lamra.todoapp.BackEnd.DatabaseNoteHelper;
import com.lamra.todoapp.BackEnd.DatabaseScheduleHelper;
import com.lamra.todoapp.BottomSheetFragments.NotesBottomSheet;
import com.lamra.todoapp.BottomSheetFragments.TaskBottomSheet;
import com.lamra.todoapp.DialogFragments.scheduleBottomSheet;
import com.lamra.todoapp.Interfaces.DialogCloseListener;
import com.lamra.todoapp.Interfaces.DialogCloseListenerNote;
import com.lamra.todoapp.Interfaces.DialogCloseListenerSchedule;
import com.lamra.todoapp.Models.notesModel;
import com.lamra.todoapp.Models.scheduleModel;
import com.lamra.todoapp.Models.taskModel;
import com.lamra.todoapp.R;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements DialogCloseListener, DialogCloseListenerNote, DialogCloseListenerSchedule, DatePickerDialog.OnDateSetListener {

    TextView ttouch,stouch,ntouch,taskAdd,scheduleAdd,noteAdd;
    public static TextView todaysDate;
    LinearLayout scheduleLayoutVisibility,noteLayoutVisibility;
    RecyclerView taskRecView,scheduleRecView,notesRecView;
    taskAdapter tAdapter;
    notesAdapter nAdapter;
    private DatabaseHelper db;
    private DatabaseNoteHelper db_note;
    private DatabaseScheduleHelper db_schedule;
    scheculeAdapter sAdapter;
    public static boolean activeUser = false;
    public ArrayList<taskModel> tList;
    public ArrayList<notesModel> nList;
    public ArrayList<scheduleModel> sList;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!activeUser){
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        }

        todaysDate = findViewById(R.id.todaysDate);

        scheduleLayoutVisibility = findViewById(R.id.scheduleLayoutVisibility);
        noteLayoutVisibility = findViewById(R.id.noteLayoutVisibility);

        db = new DatabaseHelper(this);
        db_note = new DatabaseNoteHelper(this);
        db_schedule = new DatabaseScheduleHelper(this);

        ttouch = findViewById(R.id.ttouch);
        stouch = findViewById(R.id.stouch);
        ntouch = findViewById(R.id.ntouch);

        taskAdd = findViewById(R.id.taskAdd);
        scheduleAdd = findViewById(R.id.scheduleAdd);
        noteAdd = findViewById(R.id.notesAdd);


        Calendar c = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(c.getTime());
        todaysDate.setText(currentDate);


        todaysDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.lamra.todoapp.DialogFragments.DatePicker();
                datePicker.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        ttouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scheduleRecView.getVisibility() == View.VISIBLE){
                    scheduleRecView.setVisibility(View.GONE);
                }
                if(notesRecView.getVisibility() == View.VISIBLE){
                    notesRecView.setVisibility(View.GONE);
                }
                if(taskRecView.getVisibility() == View.GONE){
                    taskRecView.setVisibility(View.VISIBLE);
                    tList = db.getAllTasks();
                    Collections.reverse(tList);
                    tAdapter.setTasks(tList);
                }else{
                    taskRecView.setVisibility(View.GONE);
                }

            }
        });

        stouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskRecView.getVisibility() == View.VISIBLE){
                    taskRecView.setVisibility(View.GONE);
                }
                if(notesRecView.getVisibility() == View.VISIBLE){
                    notesRecView.setVisibility(View.GONE);
                }
                if(scheduleRecView.getVisibility() == View.GONE){
                    scheduleRecView.setVisibility(View.VISIBLE);

                    sList = db_schedule.getAllTasks();
                    Collections.reverse(sList);
                    sAdapter.setschedules(sList);
                }else{
                    scheduleRecView.setVisibility(View.GONE);
                }
            }
        });

        ntouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskRecView.getVisibility() == View.VISIBLE){
                    taskRecView.setVisibility(View.GONE);
                }
                if(scheduleRecView.getVisibility() == View.VISIBLE){
                    scheduleRecView.setVisibility(View.GONE);
                }
                if(notesRecView.getVisibility() == View.GONE){
                    notesRecView.setVisibility(View.VISIBLE);
                    nList = db_note.getAllTasks();
                    Collections.reverse(nList);
                    nAdapter.setNotes(nList);
                }else{
                    notesRecView.setVisibility(View.GONE);
                }
            }
        });

        taskAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskBottomSheet.newInstance().show(getSupportFragmentManager(), TaskBottomSheet.TAG);
                //TaskBottomSheet taskBottomSheet = new TaskBottomSheet();
               // taskBottomSheet.show(getSupportFragmentManager(),taskBottomSheet.getTag());
            }
        });

        scheduleAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleBottomSheet.newInstance().show(getSupportFragmentManager(),scheduleBottomSheet.TAG);
            }
        });

        noteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesBottomSheet.newInstance().show(getSupportFragmentManager(), NotesBottomSheet.TAG);
            }
        });

        taskRecView = findViewById(R.id.taskRecView);
        scheduleRecView = findViewById(R.id.scheduleRecView);
        notesRecView = findViewById(R.id.noteRecView);

        taskRecView.setHasFixedSize(true);
        taskRecView.setLayoutManager(new LinearLayoutManager(this));
        notesRecView.setHasFixedSize(true);
        notesRecView.setLayoutManager(new GridLayoutManager(this,2));
        scheduleRecView.setHasFixedSize(true);
        scheduleRecView.setLayoutManager(new GridLayoutManager(this,2));

        tAdapter = new taskAdapter(db,this);
        taskRecView.setAdapter(tAdapter);
        nAdapter = new notesAdapter(db_note,this);
        notesRecView.setAdapter(nAdapter);
        sAdapter = new scheculeAdapter(db_schedule,this);
        scheduleRecView.setAdapter(sAdapter);

        tList = new ArrayList<>();
        nList = new ArrayList<>();
        sList = new ArrayList<>();

        nList = db_note.getAllTasks();
        Collections.reverse(nList);
        nAdapter.setNotes(nList);


        tList = db.getAllTasks();
        Collections.reverse(tList);
        tAdapter.setTasks(tList);


        sList = db_schedule.getAllTasks();
        Collections.reverse(sList);
        sAdapter.setschedules(sList);


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder.getClass().getName().contains("task")){
                    int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(tAdapter.getContext());
                    builder.setTitle("Delete Task");
                    builder.setMessage("Are you sure you want to delete this Task?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tAdapter.deleteTask(position);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tAdapter.notifyItemChanged(position);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    tAdapter.editItem(position);
                }
            }else if(viewHolder.getClass().getName().contains("schedule")){
                    int position = viewHolder.getAdapterPosition();
                    if (direction == ItemTouchHelper.RIGHT) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(sAdapter.getContext());
                        builder.setTitle("Delete Schedule");
                        builder.setMessage("Are you sure you want to delete this Schedule?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sAdapter.deleteSchedule(position);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sAdapter.notifyItemChanged(position);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        sAdapter.editItem(position);
                    }
                }else if(viewHolder.getClass().getName().contains("note")){
                    int position = viewHolder.getAdapterPosition();
                    if (direction == ItemTouchHelper.RIGHT) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(nAdapter.getContext());
                        builder.setTitle("Delete Note");
                        builder.setMessage("Are you sure you want to delete this Note?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nAdapter.deleteNote(position);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nAdapter.notifyItemChanged(position);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        nAdapter.editItem(position);
                    }
                }
            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if(dX>0){
                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.taskRecBackground))
                            .addActionIcon(R.drawable.ic_delete)
                            .create()
                            .decorate();
                }else{
                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.taskRecBackground))
                            .addActionIcon(R.drawable.ic_baseline_edit_24)
                            .create()
                            .decorate();
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(taskRecView);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(scheduleRecView);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(notesRecView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logOut){
            Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LogIn.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        tList = db.getAllTasks();
        Collections.reverse(tList);
         tAdapter.setTasks(tList);
         tAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleNoteDialogClose(DialogInterface dialog) {
        nList = db_note.getAllTasks();
        Collections.reverse(nList);
        nAdapter.setNotes(nList);
        nAdapter.notifyDataSetChanged();

    }

    @Override
    public void handleScheduleDialogClose(DialogInterface dialog) {
        sList = db_schedule.getAllTasks();
        Collections.reverse(sList);
        sAdapter.setschedules(sList);
        sAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance().format(c.getTime());
        todaysDate.setText(currentDate);

    }
}