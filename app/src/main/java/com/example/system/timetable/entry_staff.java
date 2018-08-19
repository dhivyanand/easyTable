package com.example.system.timetable;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class entry_staff extends AppCompatActivity {

    ArrayAdapter<String> adap;
    ArrayList<String> listview_staff, db_staff, db_reg;

    public void add_db(){

        int count = 0;

        SQLiteDatabase database = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);
        database.execSQL("delete from staff");


        while(count< listview_staff.size()){

            database.execSQL("insert into staff values('"+ db_reg.get(count) +"','"+ db_staff.get(count) +"')");
            count++;

        }

        database.close();
        finish();

    }

    public void fetch_subject(){

        SQLiteDatabase database = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("Select * from staff",null);

        if(cursor.moveToFirst()){

            do{

                listview_staff.add(cursor.getString(0)+" "+cursor.getString(1));
                db_staff.add(cursor.getString(1));
                db_reg.add(cursor.getString(0));

            }while(cursor.moveToNext());

            cursor.close();
            database.close();

        }

        cursor.close();
        database.close();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.entry_staff_details);
        getSupportActionBar().show();
        getSupportActionBar().setTitle("Staff Record");


        listview_staff = new ArrayList<String>();
        db_reg = new ArrayList<String>();
        db_staff = new ArrayList<String>();

        final EditText staff,registration;
        ImageButton add;
        Button submit;
        ListView list = (ListView)findViewById(R.id.add_staff);
        TextView empty = (TextView)findViewById(R.id._empty);


        staff = (EditText)findViewById(R.id.name);
        registration = (EditText)findViewById(R.id.registration);

        add = (ImageButton)findViewById(R.id.add);
        submit = (Button)findViewById(R.id.submit);

        fetch_subject();

        list.setEmptyView(empty);

        if(listview_staff == null) {
            list.setEmptyView(empty);
        }
        else{

            adap = new ArrayAdapter<String>(entry_staff.this,android.R.layout.simple_list_item_1, listview_staff);
            list.setAdapter(adap);

        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = staff.getText().toString() , c = registration.getText().toString();

                if(!s.isEmpty() && !c.isEmpty()){

                    if(!listview_staff.contains(c+" "+s) && !db_reg.contains(c)) {
                        listview_staff.add(c + " " + s);
                        db_reg.add(c);
                        db_staff.add(s);
                        adap.notifyDataSetChanged();
                    }
                    else
                        Toast.makeText(entry_staff.this, "Entry already exists.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(entry_staff.this, "Don\'t leave the fields empty.", Toast.LENGTH_SHORT).show();
                }

                staff.setText(null);
                registration.setText(null);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listview_staff.isEmpty()) {

                    SQLiteDatabase database = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);
                    database.execSQL("delete from staff");
                    database.close();

                    finish();

                }else{
                    add_db();
                }

            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();

    }
}
