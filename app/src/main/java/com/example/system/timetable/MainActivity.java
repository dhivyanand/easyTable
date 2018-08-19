package com.example.system.timetable;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.itextpdf.text.Document;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static boolean doesDatabaseExist(Context context, String dbName) {

        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();




    }

    void generate(){

        try {

        }catch(Exception e){}
    }


    public void create_table(){

        try {
            SQLiteDatabase mydatabase = openOrCreateDatabase("TimeTable", MODE_PRIVATE, null);
            mydatabase.execSQL("create table staff(registration varchar(10) primary key not null , name varchar(45))");
            mydatabase.execSQL("create table subject(registration varchar(10) primary key not null , name varchar(45))");
            mydatabase.execSQL("create table class_time_table(name varchar(10),day varchar(5),p1 varchar(45),p2 varchar(45),p3 varchar(45),p4 varchar(45),p5 varchar(45),p6 varchar(45),p7 varchar(45),p8 varchar(45))");
            mydatabase.execSQL("create table class_staff_table(staff_name varchar(45),subject varchar(45),class_name varchar(45))");
            mydatabase.execSQL("create table staff_time_table(name varchar(10),day varchar(5),p1 varchar(45),p2 varchar(45),p3 varchar(45),p4 varchar(45),p5 varchar(45),p6 varchar(45),p7 varchar(45),p8 varchar(45))");
            mydatabase.close();
        }catch(Exception e){return;}

        return;

    }

    public void db_not_exist(){

        getSupportActionBar().hide();
        setContentView(R.layout.front);

        final ImageButton create_db = (ImageButton)findViewById(R.id.imageButton);

        create_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                create_table();
                finish();
                startActivityForResult(new Intent(MainActivity.this,home_activity.class),1);
                startActivityForResult(new Intent(MainActivity.this,entry_subject.class),1);
                startActivityForResult(new Intent(MainActivity.this,entry_staff.class),1);


            }
        });

    }

    public void db_exist(){

        finish();
        startActivityForResult(new Intent(MainActivity.this,home_activity.class),1);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if(!doesDatabaseExist(MainActivity.this,"TimeTable")) {

            db_not_exist();

        }else{

            db_exist();

        }



    }
}
