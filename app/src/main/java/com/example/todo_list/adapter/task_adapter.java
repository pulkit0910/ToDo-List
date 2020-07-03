package com.example.todo_list.adapter;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.todo_list.MyHelper;
import com.example.todo_list.R;
import com.example.todo_list.reloadact;

import java.util.ArrayList;

public class task_adapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> titlelist = new ArrayList<>();
    private ArrayList<String>  desclist = new ArrayList<>();
    private ArrayList<String> datelist = new ArrayList<>();
    private ArrayList<String>  statuslist= new ArrayList<>();
    private int act;

    public task_adapter(Context context, ArrayList<String> titlelist, ArrayList<String> desclist, ArrayList<String> datelist, ArrayList<String> statuslist, int act) {
        this.context = context;
        this.titlelist = titlelist;
        this.desclist = desclist;
        this.datelist = datelist;
        this.statuslist = statuslist;
        this.act =act;
    }


    @Override
    public int getCount() {
        return titlelist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            grid = new View(context);
            grid = inflater.inflate(R.layout.task_layout, null);
            TextView title = (TextView) grid.findViewById(R.id.title);
            TextView date = grid.findViewById(R.id.date);
            TextView desc = grid.findViewById(R.id.description);
            TextView status = grid.findViewById(R.id.status);
            TextView update = grid.findViewById(R.id.update);
            TextView delete = grid.findViewById(R.id.delete);
            title.setText(titlelist.get(i));
            date.setText(datelist.get(i));
            desc.setText(desclist.get(i));
            status.setText(statuslist.get(i));
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MyHelper myHelper = new MyHelper(context);
                    SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    if(statuslist.get(i).equals("pending")){
                        values.put("STATUS", "completed");
                    }else{
                        values.put("STATUS", "pending");
                    }
                    int j = i+1;
                    sqLiteDatabase.update("TASKS" , values, "_id = ?" , new String[]{String.valueOf(j)});
                    Intent intent = new Intent(context.getApplicationContext(), reloadact.class);
                    intent.putExtra("activity",act);
                    context.startActivity(intent);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MyHelper myHelper = new MyHelper(context);
                    SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();

                    int j = i+1;
                    sqLiteDatabase.delete("TASKS" ,  "_id = ?" , new String[]{String.valueOf(j)});
                    Intent intent = new Intent(context.getApplicationContext(), reloadact.class);
                    intent.putExtra("activity",act);
                    context.startActivity(intent);

                }
            });

        } else {
            grid = (View) view;
        }

        return grid;
    }

}

