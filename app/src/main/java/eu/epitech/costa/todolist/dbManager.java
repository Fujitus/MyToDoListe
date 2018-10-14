package eu.epitech.costa.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by fujitus on 29/01/2018.
 */

public class dbManager extends SQLiteOpenHelper {

    private static final String DB_NAME="todolist";
    public static final String DB_TABLE="Task";
    public static final String DB_COLUMN = "TaskName";
    public static final String DB_CONTENT = "TaskContent";
    public static final String DB_DATE = "DueDate";
    public static final String DB_DONE = "Done";
    public static final String DB_TAG = "Tag";

    public dbManager(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s BOOL, %s TEXT NOT NULL);"
                                    ,DB_TABLE,DB_COLUMN,DB_CONTENT,DB_DATE,DB_DONE, DB_TAG);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        db.execSQL(query);
        onCreate(db);

    }

    public void insertNewTask(String task, String content, String date, boolean done, String tag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN, task);
        values.put(DB_CONTENT, content);
        values.put(DB_DATE, date);
        values.put(DB_DONE, done);
        values.put(DB_TAG, tag);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN + " = ?",new String[]{task});
        db.close();
    }

    public void editTask(String newTaskName, String oldTaskName, String content, String date, boolean done, String tag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN, newTaskName);
        values.put(DB_CONTENT, content);
        values.put(DB_DATE, date);
        values.put(DB_DONE, done);
        values.put(DB_TAG, tag);
        db.update(DB_TABLE, values,DB_COLUMN + " = ?",new String[]{oldTaskName});
        db.close();
    }

    public ArrayList<Todo> getTaskList(String groupBy){
        ArrayList<Todo> taskList = new ArrayList<>();
        Todo    task;
        int     index;
        int     indexContent;
        int     indexDate;
        int     indexDone;
        int     indexTag;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUMN,DB_CONTENT,DB_DATE,DB_DONE, DB_TAG},null,null, null,null, groupBy);
        while(cursor.moveToNext()){
            index = cursor.getColumnIndex(DB_COLUMN);
            indexContent = cursor.getColumnIndex(DB_CONTENT);
            indexDate = cursor.getColumnIndex(DB_DATE);
            indexDone = cursor.getColumnIndex(DB_DONE);
            indexTag = cursor.getColumnIndex(DB_TAG);
            task = new Todo(cursor.getString(index), cursor.getString(indexContent), cursor.getString(indexDate), cursor.getString(indexDone), cursor.getString(indexTag));
            taskList.add(task);
        }
        cursor.close();
        db.close();
        return taskList;
    }
}
