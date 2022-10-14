package com.lamra.todoapp.BackEnd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.lamra.todoapp.Activities.LogIn;
import com.lamra.todoapp.Activities.MainActivity;
import com.lamra.todoapp.Models.notesModel;

import java.util.ArrayList;

public class DatabaseNoteHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "TODO_NOTE_DATABASE";
    private static final String TABLE_NOTE_NAME = "TODO_NOTE_TABLE";
    private static final String COL_NOTE_1 = "ID";
    private static final String COL_NOTE_2 = "NOTE";
    private static final String COL_NOTE_3 = "DATE";
    private static final String COL_NOTE_4 = "USER";
    private SQLiteDatabase db_note;

    public DatabaseNoteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db_note) {
        db_note.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NOTE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT , NOTE TEXT, DATE TEXT,USER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db_note, int oldVersion, int newVersion) {
        db_note.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE_NAME);
        onCreate(db_note);
    }
    @SuppressLint("Range")
    public ArrayList<notesModel> getAllTasks(){
        db_note = this.getReadableDatabase();
        ArrayList<notesModel> notesList = new ArrayList<>();
        Cursor cur = null;
        db_note.beginTransaction();
        try{
            cur = db_note.rawQuery("SELECT * FROM "+TABLE_NOTE_NAME+" WHERE USER = "+"'"+ LogIn.UserName +"'" +" AND DATE = "+"'"+ MainActivity.todaysDate.getText().toString()+"'",null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        notesModel note = new notesModel();
                        note.setId(cur.getInt(cur.getColumnIndex(COL_NOTE_1)));
                        note.setNote(cur.getString(cur.getColumnIndex(COL_NOTE_2)));
                        note.setDate(cur.getString(cur.getColumnIndex(COL_NOTE_3)));
                        note.setUser(cur.getString(cur.getColumnIndex(COL_NOTE_4)));
                        notesList.add(note);
                    }while(cur.moveToNext());
                }
            }
        }finally {
            db_note.endTransaction();
            if (cur != null) {
                cur.close();
            }
        }
        return  notesList;
    }
    public void insertNote(notesModel note){
        db_note = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NOTE_2, note.getNote());
        cv.put(COL_NOTE_3,note.getDate());
        cv.put(COL_NOTE_4,note.getUser());
        db_note.insert(TABLE_NOTE_NAME, null,cv);
    }
    public void updateNote(int id, String note){
        db_note = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NOTE_2, note);
        db_note.update(TABLE_NOTE_NAME,cv, "ID=?", new String[]{String.valueOf(id)});
    }
    public void deleteNote(int id){
        db_note = this.getWritableDatabase();
        db_note.delete(TABLE_NOTE_NAME, "ID=?", new String[]{String.valueOf(id)});
    }
}
