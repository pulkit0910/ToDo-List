package com.example.todo_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {

    private static final String dbname = "taskdb";
    private static final int version = 1;

    public MyHelper(Context context){
        super(context,dbname,null,version);


    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sqlcommand = "CREATE TABLE TASKS (_id INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, DESCRIPTION TEXT, STATUS TEXT, DATE TEXT)";
        sqLiteDatabase.execSQL(sqlcommand);

//        insertdata("title1", "desc1" , "stat1" , "date1" , sqLiteDatabase);
//        insertdata("title2", "desc2" , "stat2" , "date2" , sqLiteDatabase);
//        insertdata("title3", "desc3" , "stat3" , "date3" , sqLiteDatabase);


    }

    public void insertdata(String title, String description, String status, String date , SQLiteDatabase database){
        ContentValues cvalues  =  new ContentValues();
        cvalues.put("TITLE",title);
        cvalues.put("DESCRIPTION",description);
        cvalues.put("STATUS",status);
        cvalues.put("DATE",date);
        database.insert("TASKS" , null , cvalues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
