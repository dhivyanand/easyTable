package com.example.system.timetable;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class entry_frag extends Fragment {

    int layout_res;

    ArrayList<String> classes,subjects;

    String name,registraion,elements[];

    ImageAdapter ia;

    public void populate(){

        SQLiteDatabase database = getActivity().openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);

        Cursor cursor = database.rawQuery("Select * from class_staff_table where staff_name = '"+registraion+"' ",null);

        if(cursor.moveToFirst()){

            do{

                subjects.add(cursor.getString(0));
                classes.add(cursor.getString(1));

            }while(cursor.moveToNext());

            cursor.close();
            database.close();


        }else{
            cursor.close();
            database.close();
        }

    }

    public void populate_table(){

        SQLiteDatabase database = getActivity().openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);

        for(int i=0;i<8;i++) {

            String period = "p"+(i+1);
            Cursor cursor = database.rawQuery("Select day,name from class_time_table where "+period+" = '" + registraion + "' ", null);

            if (cursor.moveToFirst()) {

                do {

                    if(cursor.getString(0).equals("Mon")){
                        elements[10+i] = cursor.getString(1);
                    }
                    if(cursor.getString(0).equals("Tue")){
                        elements[19+i] = cursor.getString(1);
                    }
                    if(cursor.getString(0).equals("Wed")){
                        elements[28+i] = cursor.getString(1);
                    }
                    if(cursor.getString(0).equals("Thu")){
                        elements[37+i] = cursor.getString(1);
                    }
                    if(cursor.getString(0).equals("Fri")){
                        elements[46+i] = cursor.getString(1);
                    }
                    if(cursor.getString(0).equals("Sat")){
                        elements[55+i] = cursor.getString(1);
                    }


                    Toast.makeText(getActivity(), cursor.getString(1), Toast.LENGTH_SHORT).show();
                } while (cursor.moveToNext());

                cursor.close();


            } else {
                cursor.close();
            }

        }
        database.close();

    }

    public entry_frag() {

    }

    @SuppressLint("ValidFragment")
    public entry_frag(String registration, String name){
        this.registraion = registration;
        this.name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.layout, container, false);

        GridView gv = (GridView)root.findViewById(R.id.grid);

        elements = new String[63];

        subjects = new ArrayList<String>();
        classes = new ArrayList<String>();

        populate();
        populate_table();

        ia = new ImageAdapter(getActivity(),elements);

        gv.setAdapter(ia);

        return root;

    }

}
