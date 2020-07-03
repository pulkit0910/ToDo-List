package com.example.todo_list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo_list.adapter.ExpandableHeightGridView;
import com.example.todo_list.adapter.task_adapter;
import com.example.todo_list.nav_activity.completed_task;
import com.example.todo_list.nav_activity.pending_task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("All Tasks");
        ExpandableHeightGridView gridView = findViewById(R.id.grid);
        gridView.setExpanded(true);
        TextView notask = findViewById(R.id.notask);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open("","Add Task");
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.completed_tasks, R.id.pending_tasks)
                .setDrawerLayout(drawer)
                .build();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);


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
                title.add(cursor.getString(0));
                description.add(cursor.getString(1));
                status.add(cursor.getString(2));
                date.add(cursor.getString(3));
                sbuilder.append(title + " " + description + " " + status + " " + date);

            }while (cursor.moveToNext());
            //Toast.makeText(this, sbuilder.toString(), Toast.LENGTH_SHORT).show();

            task_adapter adapter = new task_adapter(MainActivity.this, title,description,date,status,1);
            gridView.setAdapter(adapter);
            notask.setVisibility(View.GONE);
        }else{
            notask.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.completed_tasks) {
            Intent intent = new Intent(MainActivity.this, completed_task.class);
            startActivity(intent);

        }else if (id == R.id.pending_tasks){
            Intent intent = new Intent(MainActivity.this, pending_task.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.search){
            /*
            adding func
             */
        }

        return super.onOptionsItemSelected(item);
    }

    public void open(final String message, String title){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_layout, null);
        alertDialogBuilder.setView(view);
        final EditText input1 = view.findViewById(R.id.input1);
        final EditText input2 =  view.findViewById(R.id.input2);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        input1.setLayoutParams(lp);
//        alertDialogBuilder.setView(input1);
//        input2.setLayoutParams(lp);
//        alertDialogBuilder.setView(input2);
        alertDialogBuilder.setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                MyHelper myHelper = new MyHelper(MainActivity.this);
                SQLiteDatabase sqlitedb = myHelper.getWritableDatabase();
                Date currentTime = Calendar.getInstance().getTime();
                String curtimedate = currentTime.toString().replace(" GMT+05:30","");

                myHelper.insertdata( input1.getText().toString() , input2.getText().toString(),"pending",curtimedate,sqlitedb);
                Intent intent = getIntent();
                finish();
                startActivity(intent);


                //Toast.makeText(MainActivity.this, input1.getText().toString() + input2.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}