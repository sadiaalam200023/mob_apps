package com.example.todolist;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


public class Databasehelper extends SQLiteOpenHelper {

   public static final String Database_name = "Todolist";
    public static final int Dbversion = 1;
    public static final String TTable_name = "Tasks";
    private static final String COLUMN_ID = "id";
    private static final String task_list = "Task_Description";
    public Databasehelper(Context context) {
        super(context, Database_name,null,Dbversion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TTable_name + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                task_list + " TEXT)"; // Ensure task_list matches the column name
        db.execSQL(query);
        Log.d("Databasehelper","table"+TTable_name);
    }
    public boolean addNewTask(String Task_Description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(task_list, Task_Description);
        long result = db.insert(TTable_name, null, values);
        db.close();
        Log.d("DatabaseHelper", "Task added: " + Task_Description + ", Result: " + result);
        return result != -1;
       }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(oldVersion<2){db.execSQL("ALTER TABLE " + TTable_name + " ADD COLUMN " + task_list + " TEXT");}
        }
    public boolean updateTask(int id, String Task_Description ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(task_list, Task_Description);
        db.update(TTable_name, values,COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        int rowsUpdated = db.update(TTable_name, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return rowsUpdated > 0;

    }
    public boolean deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TTable_name, COLUMN_ID+ " = ?", new String[]{String.valueOf(id)});
        int result = db.delete(TTable_name, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result>0;
    }
    public int getTaskId(String taskDescription) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM " + TTable_name + " WHERE " + task_list + " = ?", new String[]{taskDescription});

        int taskId = -1;
        if (cursor.moveToFirst()) {
            taskId = cursor.getInt(0);
        }
        cursor.close();
        return taskId;
    }
    public ArrayList<String> getallTasks(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TTable_name, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String taskDescription = cursor.getString(cursor.getColumnIndex(task_list));
                taskList.add(taskDescription);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskList;
    }

    }




