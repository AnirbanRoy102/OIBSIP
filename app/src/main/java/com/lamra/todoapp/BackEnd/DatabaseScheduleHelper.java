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
import com.lamra.todoapp.Models.scheduleModel;

import java.util.ArrayList;

public class DatabaseScheduleHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "TODO_SCHEDULE_DATABASE";
    private static final String TABLE_NAME = "TODO_SCHEDULE_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "STATUS";
    private static final String COL_3 = "INFO";
    private static final String COL_4 = "DATE";
    private static final String COL_5 = "USER";
    private static final String COL_6 = "TIME";
    private SQLiteDatabase db_schedule;

    public DatabaseScheduleHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,STATUS INTEGER,INFO TEXT,DATE TEXT,USER TEXT,TIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void insertSchedule(scheduleModel schedule){
        db_schedule =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2,schedule.getStatus());
        cv.put(COL_3,schedule.getAddtionalInfo());
        cv.put(COL_4,schedule.getDate());
        cv.put(COL_5,schedule.getUser());
        cv.put(COL_6,schedule.getTime());
        db_schedule.insert(TABLE_NAME,null,cv);
    }
    @SuppressLint("Range")
    public ArrayList<scheduleModel> getAllTasks(){
        db_schedule = this.getReadableDatabase();
        ArrayList<scheduleModel> scheduleList = new ArrayList<>();
        Cursor cur = null;
        db_schedule.beginTransaction();
        try{
            cur = db_schedule.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE USER = "+"'"+LogIn.UserName +"'"+" AND DATE = "+"'"+ MainActivity.todaysDate.getText().toString()+"'",null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        scheduleModel schedule = new scheduleModel();
                        schedule.setId(cur.getInt(cur.getColumnIndex(COL_1)));
                        schedule.setStatus(cur.getInt(cur.getColumnIndex(COL_2)));
                        schedule.setAddtionalInfo(cur.getString(cur.getColumnIndex(COL_3)));
                        schedule.setDate(cur.getString(cur.getColumnIndex(COL_4)));
                        schedule.setUser(cur.getString(cur.getColumnIndex(COL_5)));
                        schedule.setTime(cur.getString(cur.getColumnIndex(COL_6)));
                        scheduleList.add(schedule);
                    }while(cur.moveToNext());
                }
            }
        }finally {
            db_schedule.endTransaction();
            if (cur != null) {
                cur.close();
            }
        }
        return  scheduleList;
    }
    public void updateStatus(int id,int status){
        db_schedule = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2,status);
        db_schedule.update(TABLE_NAME, cv, "ID=?", new String[]{String.valueOf(id)});
    }
    public void updateInfo(int id, String AdditionalInfo){
        db_schedule = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_3, AdditionalInfo);
        db_schedule.update(TABLE_NAME,cv, "ID=?", new String[]{String.valueOf(id)});
    }

    public void deleteSchedule(int id){
        db_schedule = this.getWritableDatabase();
        db_schedule.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
    }
}
