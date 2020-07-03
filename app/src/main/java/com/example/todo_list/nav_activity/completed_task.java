package com.example.todo_list.nav_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo_list.MainActivity;
import com.example.todo_list.MyHelper;
import com.example.todo_list.R;
import com.example.todo_list.adapter.ExpandableHeightGridView;
import com.example.todo_list.adapter.task_adapter;

import java.util.ArrayList;

public class completed_task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Completed Tasks");
        ExpandableHeightGridView gridView = findViewById(R.id.grid);
        gridView.setExpanded(true);
        TextView notask = findViewById(R.id.notask);

        MyHelper myhelper = new MyHelper(this);
        SQLiteDatabase sqlitedb = myhelper.getReadableDatabase();
        StringBuilder sbuilder = new StringBuilder();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();

        Cursor cursor = sqlitedb.rawQuery("SELECT TITLE, DESCRIPTION, STATUS, DATE FROM TASKS",new String[]{});
        if(cursor !=null){
            cursor.moveToFirst();
            Toast.makeText(this, "cursor is not null", Toast.LENGTH_SHORT).show();

        }
        String count = "SELECT count(*) FROM TASKS";
        Cursor mcursor = sqlitedb.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){
            do{
                if(cursor.getString(2).equals("completed")){
                    title.add(cursor.getString(0));
                    description.add(cursor.getString(1));
                    status.add(cursor.getString(2));
                    date.add(cursor.getString(3));
                }

            }while (cursor.moveToNext());
            //Toast.makeText(this, sbuilder.toString(), Toast.LENGTH_SHORT).show();

            if(title.size() != 0){
                task_adapter adapter = new task_adapter(completed_task.this, title,description,date,status,3);
                gridView.setAdapter(adapter);
                notask.setVisibility(View.GONE);
            }else{
                notask.setVisibility(View.VISIBLE);
            }
        }else{
            notask.setVisibility(View.VISIBLE);
        }


    }
}