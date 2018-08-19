package com.example.system.timetable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


public class home_frag extends Fragment implements View.OnClickListener {

    ArrayList<String> staff_name,subject_name,staff_code,subject_code,grid_filler;
    private String class_name;
    String elements[];
    ImageAdapter ia;

    public ArrayList<String> filter(String period,String day){

        ArrayList<String> stf_code = new ArrayList<String>();

        stf_code.addAll(staff_code);

        SQLiteDatabase database = getActivity().openOrCreateDatabase("TimeTable", getActivity().MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("Select * from class_time_table where day = '"+day+"' AND name != '"+class_name+"' ",null);

        if(cursor.moveToFirst()) {
            do {

                stf_code.remove(cursor.getString(cursor.getColumnIndex(period)));

            } while (cursor.moveToNext());

            cursor.close();
            database.close();
        }else{
            cursor.close();
            database.close();
        }

      return stf_code;

    }



    public void showMenu (final View view, final ArrayList<String> code,final int pos,final String period,final String day)
    {
        PopupMenu menu = new PopupMenu (getActivity(), view);
        menu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener ()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {

                String codes = item.getTitle().toString(),table;

                table = staff_code.get(staff_name.indexOf(codes.substring(codes.indexOf('\n')+1)));

                elements[pos] = subject_name.get(staff_name.indexOf(codes.substring(codes.indexOf('\n')+1)));

                SQLiteDatabase database = getActivity().openOrCreateDatabase("TimeTable", getActivity().MODE_PRIVATE,null);

                database.execSQL("update class_time_table set '"+period+"' = '"+table+"' where day = '"+day+"' AND name = '"+class_name+"' ");

                database.close();

                ia.notifyDataSetChanged();

                return true;

            }
        });

        for(int i=0;i<code.size();i++) {
            menu.getMenu().add(subject_name.get(staff_code.indexOf(code.get(i)))+" by "+'\n'+staff_name.get(staff_code.indexOf(code.get(i))));

        }
        menu.show();

    }

    public boolean populate(){

        SQLiteDatabase database = getActivity().openOrCreateDatabase("TimeTable", getActivity().MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("Select * from class_staff_table where class_name = '" + class_name + "' ",null);

        if(cursor.moveToFirst()){
            do{

                staff_code.add(cursor.getString(0));
                subject_code.add(cursor.getString(1));

                Cursor test = database.rawQuery("select * from staff where registration = '"+cursor.getString(0)+"' ",null);
                if(test.moveToFirst())
                    staff_name.add(test.getString(1));

                test = database.rawQuery("select * from subject where registration = '"+cursor.getString(1)+"' ",null);

                if(test.moveToFirst())
                    subject_name.add(test.getString(1));

            }while(cursor.moveToNext());

            cursor.close();
            database.close();

            return true;

        }else{
            cursor.close();
            database.close();
        }

        return false;

    }

    public void populate_table(){

        SQLiteDatabase database = getActivity().openOrCreateDatabase("TimeTable", getActivity().MODE_PRIVATE,null);

        //Monday
        Cursor cursor = database.rawQuery("Select * from class_time_table where name = '" + class_name + "' AND day = 'Mon' ",null);

        String stf;
        if(cursor.moveToFirst()){
            int i=2;
              for(int count = 10 ; count <= 17 ; count++) {
                       if(! (stf = cursor.getString(i)).equals("nil")){
                           elements[count] = subject_name.get(staff_code.indexOf(stf));
                       }
                       i++;
                }
            cursor.close();
        }else{
            cursor.close();
        }

        //tuesday

        cursor = database.rawQuery("Select * from class_time_table where name = '" + class_name + "' AND day = 'Tue' ",null);

        if(cursor.moveToFirst()){
            int i=2;
                for(int count = 19 ; count <= 26 ; count++) {
                    if(! (stf = cursor.getString(i)).equals("nil")){
                        elements[count] = subject_name.get(staff_code.indexOf(stf));
                    }
                    i++;
                }
            cursor.close();
        }else{
            cursor.close();
        }

        //wednesday

        cursor = database.rawQuery("Select * from class_time_table where name = '" + class_name + "' AND day = 'Wed' ",null);

        if(cursor.moveToFirst()){
            int i=2;
                for(int count = 28 ; count <= 35 ; count++) {
                    if(! (stf = cursor.getString(i)).equals("nil")){
                        elements[count] = subject_name.get(staff_code.indexOf(stf));
                    }
                    i++;
                }
            cursor.close();
        }else{
            cursor.close();
        }

        //thursday

        cursor = database.rawQuery("Select * from class_time_table where name = '" + class_name + "' AND day = 'Thu' ",null);

        if(cursor.moveToFirst()){
            int i=2;
                for(int count = 37 ; count <= 44 ; count++) {
                    if(! (stf = cursor.getString(i)).equals("nil")){
                        elements[count] = subject_name.get(staff_code.indexOf(stf));
                    }
                    i++;
                }
            cursor.close();
        }else{
            cursor.close();
        }

        //friday

        cursor = database.rawQuery("Select * from class_time_table where name = '" + class_name + "' AND day = 'Fri' ",null);

        if(cursor.moveToFirst()){
            int i=2;
                for(int count = 46 ; count <= 53 ; count++) {
                    if(! (stf = cursor.getString(i)).equals("nil")){
                        elements[count] = subject_name.get(staff_code.indexOf(stf));
                    }
                    i++;
                }
            cursor.close();
        }else{
            cursor.close();
        }

        //saturday

        cursor = database.rawQuery("Select * from class_time_table where name = '" + class_name + "' AND day = 'Sat' ",null);

        if(cursor.moveToFirst()){
            int i=2;
                for(int count = 55 ; count < 63 ; count++) {
                    if(! (stf = cursor.getString(i)).equals("nil")){
                        elements[count] = subject_name.get(staff_code.indexOf(stf));
                    }
                    i++;
                }
            cursor.close();
            database.close();
        }else{
            cursor.close();
            database.close();
        }



    }

    public home_frag() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public home_frag(String class_name) {
        // Required empty public constructor
        this.class_name = class_name;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.layout, container, false);

        GridView gv = (GridView)root.findViewById(R.id.grid);

        elements = new String[63];



        ia = new ImageAdapter(getActivity(),elements);

        gv.setAdapter(ia);

        staff_name = new ArrayList<String>();
        subject_name = new ArrayList<String>();
        staff_code = new ArrayList<String>();
        subject_code = new ArrayList<String>();
        grid_filler = new ArrayList<String>();

        populate();
        populate_table();

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                if(!(position < 9 || position % 9 == 0))
                {
                    if(position > 9 && position < 18){
                        // col1
                        String period = "p"+((position % 10) + 1);

                        showMenu(v,filter(period,"Mon"),position,period,"Mon");

                    }else if (position > 18 && position < 27){
                        // col2
                        String period = "p"+((position-9) % 10 + 1);

                        showMenu(v,filter(period,"Tue"),position,period,"Tue");

                    }else if (position >27 && position < 36){
                        // col3
                        String period = "p"+((position-8) % 20 + 1);

                        showMenu(v,filter(period,"Wed"),position,period,"Wed");

                    }else if (position > 36 && position < 45){
                        // col4
                        String period = "p"+((position-7) % 30 + 1);

                        showMenu(v,filter(period,"Thu"),position,period,"Thu");


                    }else if (position > 45 && position < 54){
                        // col5
                        String period = "p"+((position-6) % 40 + 1);

                        showMenu(v,filter(period,"Fri"),position,period,"Fri");


                    }else if (position > 54){
                        // col6
                        String period = "p"+((position-5) % 50 + 1);

                        showMenu(v,filter(period,"Sat"),position,period,"Sat");

                    }

                }

                ia.notifyDataSetChanged();



            }
        });


        return root;
    }

    @Override
    public void onClick(View v){


    }
}
