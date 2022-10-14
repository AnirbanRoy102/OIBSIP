package com.lamra.todoapp.BackEnd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import static com.lamra.todoapp.Activities.LogIn.UserName;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lamra.todoapp.Activities.MainActivity;
import com.lamra.todoapp.Models.taskModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "STATUS";
    private static final String COL_4 = "DATE";
    private static final String COL_5 = "USER";
    private SQLiteDatabase db;



    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER, DATE TEXT,USER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void insertTask(taskModel task){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, task.getTask());
        cv.put(COL_3, 0);
        cv.put(COL_4,task.getDate());
        cv.put(COL_5,task.getUser());
        db.insert(TABLE_NAME, null,cv);
    }

    @SuppressLint("Range")
    public ArrayList<taskModel> getAllTasks(){
        db = this.getReadableDatabase();
        ArrayList<taskModel> tasksList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE USER = "+"'"+UserName+"'"+ " AND DATE = "+"'"+MainActivity.todaysDate.getText().toString()+"'",null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        taskModel task = new taskModel();
                        task.setId(cur.getInt(cur.getColumnIndex(COL_1)));
                        task.setTask(cur.getString(cur.getColumnIndex(COL_2)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(COL_3)));
                        task.setDate(cur.getString(cur.getColumnIndex(COL_4)));
                        task.setUser(cur.getString(cur.getColumnIndex(COL_5)));
                        tasksList.add(task);
                    }while(cur.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            if (cur != null) {
                cur.close();
            }
        }
        return  tasksList;
    }
    public void updateStatus(int id,int status){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_3,status);
        db.update(TABLE_NAME, cv, "ID=?", new String[]{String.valueOf(id)});
    }
    public void updateTask(int id, String task){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, task);
        db.update(TABLE_NAME,cv, "ID=?", new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
    }
}
