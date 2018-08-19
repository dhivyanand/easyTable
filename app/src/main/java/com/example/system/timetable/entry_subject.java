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

public class entry_subject extends AppCompatActivity {

    ArrayAdapter<String> adap;
    ArrayList<String> listview_subject,db_sub,db_code;

    public void add_db(){

        int count = 0;

        SQLiteDatabase database = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);
        database.execSQL("delete from subject");


        while(count<listview_subject.size()){

            database.execSQL("insert into subject values('"+ db_code.get(count) +"','"+ db_sub.get(count) +"')");
            count++;

        }

        database.close();
        finish();

    }

    public void fetch_subject(){

        SQLiteDatabase database = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("Select * from subject",null);

        if(cursor.moveToFirst()){
            do{

                listview_subject.add(cursor.getString(0)+" "+cursor.getString(1));
                db_sub.add(cursor.getString(1));
                db_code.add(cursor.getString(0));

            }while(cursor.moveToNext());

            cursor.close();
            database.close();

        }

        cursor.close();
        database.close();

    }

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.entry_subject_details);
        getSupportActionBar().show();
        getSupportActionBar().setTitle("Subject Record");


        listview_subject = new ArrayList<String>();
        db_code = new ArrayList<String>();
        db_sub = new ArrayList<String>();

        final EditText subject,code;
        ImageButton add;
        Button submit;
        ListView list = (ListView)findViewById(R.id.add_list);
        TextView empty = (TextView)findViewById(R.id.empty);


        subject = (EditText)findViewById(R.id.subject);
        code = (EditText)findViewById(R.id.code);

        add = (ImageButton)findViewById(R.id.add);
        submit = (Button)findViewById(R.id.done);

        fetch_subject();

        list.setEmptyView(empty);

        if(listview_subject == null) {
            list.setEmptyView(empty);
        }
        else{

            adap = new ArrayAdapter<String>(entry_subject.this,android.R.layout.simple_list_item_1, listview_subject);
            list.setAdapter(adap);

        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = subject.getText().toString() , c = code.getText().toString();

                if(!s.isEmpty() && !c.isEmpty()){

                 if(!listview_subject.contains(c+" "+s)) {
                       listview_subject.add(c + " " + s);
                       db_code.add(c);
                       db_sub.add(s);
                       adap.notifyDataSetChanged();
                   }
                   else
                       Toast.makeText(entry_subject.this, "Entry already exists.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(entry_subject.this, "Don\'t leave the fields empty.", Toast.LENGTH_SHORT).show();
                }

                subject.setText(null);
                code.setText(null);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listview_subject.isEmpty()) {

                    SQLiteDatabase database = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);
                    database.execSQL("delete from subject");
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
