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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class class_entry extends AppCompatActivity {

    ArrayList<String> array_subject_name,array_staff_name,array_subject_code,array_staff_code,array_listview, db_staff, db_subject;

    ArrayAdapter<String> list_adap,subject_adap,staff_adap;

    ImageButton add;

    Button done;

    ListView list;

    EditText name;

    Spinner subject,staff;

    String class_name;

    public void add_to_list(){

       SQLiteDatabase database = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);

       String sub = subject.getSelectedItem().toString() , stf = staff.getSelectedItem().toString();

       array_listview.add(sub+" "+stf);

       subject_adap.remove(sub);
       staff_adap.remove(stf);

       array_subject_name.remove(sub);
       array_staff_name.remove(stf);

       database.execSQL("insert into class_staff_table values('"+ array_staff_code.get(db_staff.indexOf(stf)) +"','"+ array_subject_code.get(db_subject.indexOf(sub)) +"','"+ class_name +"')");

       database.close();

    }

    public boolean check_if_class_available(String name){

        SQLiteDatabase database = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("Select * from class_time_table where day = 'Mon' ",null);

        if(cursor.moveToFirst()){

            do{

                if(cursor.getString(0).equals(name))
                    return true;

            }while(cursor.moveToNext());

            cursor.close();
            database.close();

        }else{
            cursor.close();
            database.close();
        }
        return false;
    }

    public void delete_data(){
        if(!class_name.equals("")) {
            SQLiteDatabase mydatabase = openOrCreateDatabase("TimeTable", MODE_PRIVATE, null);
            mydatabase.execSQL("delete from class_staff_table where class_name = '" + class_name + "' ");
            mydatabase.close();
        }
    }

    public void class_time_table(String name){

        String days[] = {"","Mon","Tue","Wed","Thu","Fri","Sat"},nil = "nil";

        SQLiteDatabase mydatabase = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);

        for(int i=1;i<7;i++) {
            mydatabase.execSQL("insert into class_time_table values('" + name + "','" + days[i] + "','" + nil + "','" + nil + "','" + nil + "','" + nil + "','" + nil + "','" + nil + "','" + nil + "','" + nil + "') ");
        }
        mydatabase.close();
        finish();

    }

    public void populate_spinner_subject(){

        SQLiteDatabase database = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("Select * from subject",null);

        if(cursor.moveToFirst()){

            do{

                array_subject_code.add(cursor.getString(0));
                array_subject_name.add(cursor.getString(1));

            }while(cursor.moveToNext());

            cursor.close();
            database.close();

        }else{
            cursor.close();
            database.close();
        }

        subject_adap = new ArrayAdapter<String>(class_entry.this,android.R.layout.simple_spinner_dropdown_item,array_subject_name);

        subject.setAdapter(subject_adap);

    }

    public void populate_spinner_staff(){

        SQLiteDatabase database = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("Select * from staff",null);

        if(cursor.moveToFirst()){

            do{

                array_staff_code.add(cursor.getString(0));
                array_staff_name.add(cursor.getString(1));

            }while(cursor.moveToNext());

            cursor.close();
            database.close();

        }else{
            cursor.close();
            database.close();
        }

        staff_adap = new ArrayAdapter<String>(class_entry.this,android.R.layout.simple_spinner_dropdown_item,array_staff_name);

        staff.setAdapter(staff_adap);

    }

    @Override
    public void onBackPressed(){
        delete_data();
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_menu_frag);

        add = (ImageButton)findViewById(R.id.add);
        done = (Button)findViewById(R.id.done);
        list = (ListView)findViewById(R.id.add_list);
        name = (EditText)findViewById(R.id.name);
        subject = (Spinner)findViewById(R.id.subject);
        staff = (Spinner)findViewById(R.id.staff);

        array_staff_name = new ArrayList<String>();
        array_subject_name = new ArrayList<String>();
        array_staff_code = new ArrayList<String>();
        array_subject_code = new ArrayList<String>();
        array_listview = new ArrayList<String>();
        db_staff = new ArrayList<String>();
        db_subject = new ArrayList<String>();

        list_adap = new ArrayAdapter<String>(class_entry.this,android.R.layout.simple_list_item_1,array_listview);

        populate_spinner_staff();
        populate_spinner_subject();

        class_name = new String();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(subject_adap.getCount()>0 && staff_adap.getCount()>0)
                if(name.isEnabled()){
                    if(check_if_class_available(name.getText().toString())) {
                        Toast.makeText(class_entry.this, "A class with same registraion already available.", Toast.LENGTH_SHORT).show();
                    }
                    else if(!name.getText().toString().equals("")){
                        class_name = name.getText().toString();
                        db_staff.addAll(array_staff_name);
                        db_subject.addAll(array_subject_name);
                        add_to_list();
                        list.setAdapter(list_adap);
                        name.setEnabled(false);
                    }
                    else{
                        Toast.makeText(class_entry.this, "Please enter a class registraion.", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    add_to_list();
                    list_adap.notifyDataSetChanged();
                }

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!class_name.equals("")) {
                    try {
                        class_time_table(class_name);
                    } catch (Exception e) {
                    }
                }
            }
        });



    }
}
