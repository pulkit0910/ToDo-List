package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.todo_list.nav_activity.completed_task;
import com.example.todo_list.nav_activity.pending_task;

public class reloadact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reloadact);
        int act = getIntent().getIntExtra("activity",1);

        if(act == 1){
            Intent intent = new Intent(reloadact.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else if(act == 2){
            Intent intent = new Intent(reloadact.this, pending_task.class);
            startActivity(intent);
            finish();
        }else{

            Intent intent = new Intent(reloadact.this, completed_task.class);
            startActivity(intent);
            finish();
        }



    }
}