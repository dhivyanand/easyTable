package com.example.system.timetable;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class home_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> arrayList,staff_name,subject_name,staff_code,subject_code,pop_sub,pop_stf, popup;
    ArrayAdapter<String> adap;
    Toolbar toolbar;

    static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }


    public void populate_table(String registration)  {

        try {
            SQLiteDatabase database = openOrCreateDatabase("TimeTable", MODE_PRIVATE, null);

            String elements[] = new String[63];

            for (int i = 0; i < 8; i++) {

                String period = "p" + (i + 1);
                Cursor cursor = database.rawQuery("Select day,name from class_time_table where " + period + " = '" + registration + "' ", null);

                if (cursor.moveToFirst()) {

                    do {

                        if (cursor.getString(0).equals("Mon")) {
                            elements[10 + i] = cursor.getString(1);
                        }
                        if (cursor.getString(0).equals("Tue")) {
                            elements[19 + i] = cursor.getString(1);
                        }
                        if (cursor.getString(0).equals("Wed")) {
                            elements[28 + i] = cursor.getString(1);
                        }
                        if (cursor.getString(0).equals("Thu")) {
                            elements[37 + i] = cursor.getString(1);
                        }
                        if (cursor.getString(0).equals("Fri")) {
                            elements[46 + i] = cursor.getString(1);
                        }
                        if (cursor.getString(0).equals("Sat")) {
                            elements[55 + i] = cursor.getString(1);
                        }


                        Document document = new Document();
                        document.open();

                        Paragraph header = new Paragraph();

                        addEmptyLine(header, 1);

                        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                                Font.BOLD);

                        header.add(new Paragraph("Staff : " + registration, catFont));

                        addEmptyLine(header, 1);

                        PdfPTable table = new PdfPTable(new float[]{2, 2, 2, 2, 2, 2, 2, 2, 2});

                        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.getDefaultCell().setFixedHeight(20);

                        table.addCell("");
                        table.addCell("1");
                        table.addCell("2");
                        table.addCell("3");
                        table.addCell("4");
                        table.addCell("5");
                        table.addCell("6");
                        table.addCell("7");
                        table.addCell("8");

                        table.setHeaderRows(1);
                        PdfPCell[] cells = table.getRow(0).getCells();

                        for (int j = 0; j < cells.length; j++) {
                            cells[j].setBackgroundColor(BaseColor.GRAY);
                        }

                        table.addCell("Mon");
                        for (int cc = 10; cc < 18; cc++)
                            table.addCell(elements[cc]);

                        table.addCell("Tue");
                        for (int cc = 19; cc < 27; cc++)
                            table.addCell(elements[cc]);

                        table.addCell("Wed");
                        for (int cc = 28; cc < 36; cc++)
                            table.addCell(elements[cc]);

                        table.addCell("Thu");
                        for (int cc = 37; cc < 45; cc++)
                            table.addCell(elements[cc]);

                        table.addCell("Fri");
                        for (int cc = 46; cc < 54; cc++)
                            table.addCell(elements[cc]);

                        table.addCell("Sat");
                        for (int cc = 55; cc < 63; cc++)
                            table.addCell(elements[cc]);
                        try {

                            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "/easyTable");
                            f.mkdir();
                            File file = new File(f, registration + ".pdf");

                            file.createNewFile();

                            PdfWriter.getInstance(document, new FileOutputStream(file));

                            document.open();

                            addEmptyLine(header, 2);

                            PdfPTable table2 = new PdfPTable(new float[]{2, 2});
                            table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            table2.getDefaultCell().setFixedHeight(20);
                            table2.addCell("Class");
                            table2.addCell("Subject");

                            table2.setHeaderRows(1);

                            PdfPCell[] cell = table2.getRow(0).getCells();

                            for (int j = 0; j < cell.length; j++) {
                                cell[j].setBackgroundColor(BaseColor.GRAY);
                            }

                            String days[] = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

                            for (i = 0; i < 6; i++) {

                                table2.addCell(days[i]);
                                table2.addCell(" ");

                            }

                            document.add(header);
                            document.add(table);

                            Paragraph p = new Paragraph();
                            addEmptyLine(p, 2);
                            //document.add(p);
                            //document.add(table2);

                            document.close();

                        } catch (Exception e) {
                            Toast.makeText(home_activity.this, e.toString(), Toast.LENGTH_SHORT).show();

                        }

                        //  Toast.makeText(getActivity(), cursor.getString(1), Toast.LENGTH_SHORT).show();
                    } while (cursor.moveToNext());

                    cursor.close();


                } else {
                    cursor.close();
                }

            }
            database.close();
        }catch(Exception e){
            Toast.makeText(home_activity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    static void _class(){

        try{
            Document document = new Document();

            Paragraph header = new Paragraph();

            addEmptyLine(header, 1);

            Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                    Font.BOLD);

            header.add(new Paragraph("Class : ", catFont));

            addEmptyLine(header, 1);

            PdfPTable table = new PdfPTable(new float[] { 2, 2, 2,2,2,2,2,2,2 });

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setFixedHeight(20);


            table.addCell("");
            table.addCell("1");
            table.addCell("2");
            table.addCell("3");
            table.addCell("4");
            table.addCell("5");
            table.addCell("6");
            table.addCell("7");
            table.addCell("8");

            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }

            String day[] = {"Mon","Tue","Wed","Thu","Fri","Sat"};

            for (int i=0;i<6;i++){
                table.addCell(day[i]);
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
            }
            PdfWriter.getInstance(document, new FileOutputStream("//home//System//sample3.pdf"));
            document.open();

            addEmptyLine(header, 2);


            PdfPTable table2 = new PdfPTable(new float[] { 2, 2 });
            table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.getDefaultCell().setFixedHeight(20);
            table2.addCell("Subject");
            table2.addCell("Staff");

            table2.setHeaderRows(1);

            PdfPCell[] cell = table2.getRow(0).getCells();



            for (int j=0;j<cell.length;j++){
                cell[j].setBackgroundColor(BaseColor.GRAY);
            }

            String days[] = {"Mon","Tue","Wed","Thu","Fri","Sat"};

            for (int i=0;i<6;i++){
                table2.addCell(days[i]);
                table2.addCell(" ");

            }
            document.add(header);
            document.add(table);

            Paragraph p = new Paragraph();
            addEmptyLine(p,2);
            document.add(p);
            document.add(table2);


            document.close();
            System.out.println("Done");

        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

    static void _staff(){

        try{

            Document document = new Document();

            Paragraph header = new Paragraph();

            addEmptyLine(header, 1);

            Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                    Font.BOLD);

            header.add(new Paragraph("Staff : ", catFont));

            addEmptyLine(header, 1);

            PdfPTable table = new PdfPTable(new float[] { 2, 2, 2,2,2,2,2,2,2 });

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setFixedHeight(20);


            table.addCell("");
            table.addCell("1");
            table.addCell("2");
            table.addCell("3");
            table.addCell("4");
            table.addCell("5");
            table.addCell("6");
            table.addCell("7");
            table.addCell("8");

            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }

            String day[] = {"Mon","Tue","Wed","Thu","Fri","Sat"};

            for (int i=0;i<6;i++){
                table.addCell(day[i]);
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
            }
            PdfWriter.getInstance(document, new FileOutputStream("//home//System//sample3.pdf"));
            document.open();

            addEmptyLine(header, 2);


            PdfPTable table2 = new PdfPTable(new float[] { 2, 2 });
            table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.getDefaultCell().setFixedHeight(20);
            table2.addCell("Class");
            table2.addCell("Subject");

            table2.setHeaderRows(1);

            PdfPCell[] cell = table2.getRow(0).getCells();



            for (int j=0;j<cell.length;j++){
                cell[j].setBackgroundColor(BaseColor.GRAY);
            }

            String days[] = {"Mon","Tue","Wed","Thu","Fri","Sat"};

            for (int i=0;i<6;i++){
                table2.addCell(days[i]);
                table2.addCell(" ");

            }
            document.add(header);
            document.add(table);

            Paragraph p = new Paragraph();
            addEmptyLine(p,2);
            document.add(p);
            document.add(table2);


            document.close();
            System.out.println("Done");


        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

    ArrayList<String> populate_class(){

        ArrayList<String> classes = new ArrayList<String>();

        SQLiteDatabase database = openOrCreateDatabase("TimeTable", MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("Select * from class_time_table where day = 'Mon' ",null);

        if(cursor.moveToFirst()){
            do{

                classes.add(cursor.getString(0));

            }while(cursor.moveToNext());

            cursor.close();

        }else{
            cursor.close();
        }


        return classes;
    }



    void generate() throws Exception{

    try {
        SQLiteDatabase database = openOrCreateDatabase("TimeTable", MODE_PRIVATE, null);
        Cursor cls = database.rawQuery("Select * from class_time_table where day = 'Mon' ", null);

        if (cls.moveToFirst()) {
            do {

                ArrayList<String> stf = new ArrayList<String>(),sub = new ArrayList<String>();

                Cursor pop = database.rawQuery("Select * from class_staff_table where class_name = '" + cls.getString(0) + "'", null);

                if(pop.moveToFirst()){

                    do{
                        stf.add(pop.getString(0));
                        sub.add(pop.getString(1));
                    }while (pop.moveToNext());
                    pop.close();
                }

                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "/easyTable");
                f.mkdir();
                File file = new File(f, cls.getString(0) + ".pdf");

                file.createNewFile();

                Cursor cursor = database.rawQuery("Select * from class_time_table where name = '" + cls.getString(0) + "'", null);

                Document document = new Document();
                document.open();

                Paragraph header = new Paragraph();

                addEmptyLine(header, 1);

                Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                        Font.BOLD);

                header.add(new Paragraph("Class : " + cls.getString(0), catFont));

                addEmptyLine(header, 1);

                PdfPTable table = new PdfPTable(new float[]{2, 2, 2, 2, 2, 2, 2, 2, 2});

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.getDefaultCell().setFixedHeight(20);

                table.addCell("");
                table.addCell("1");
                table.addCell("2");
                table.addCell("3");
                table.addCell("4");
                table.addCell("5");
                table.addCell("6");
                table.addCell("7");
                table.addCell("8");

                table.setHeaderRows(1);
                PdfPCell[] cells = table.getRow(0).getCells();

                for (int j = 0; j < cells.length; j++) {
                    cells[j].setBackgroundColor(BaseColor.GRAY);
                }

                String day[] = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

                if (cursor.moveToFirst()) {
                    do {



                            table.addCell(cursor.getString(1));

                            Cursor test;
                            try {
                                test = database.rawQuery("Select name from subject where registration = '" + sub.get(stf.indexOf(cursor.getString(2))) + "'", null);
                                if(test.moveToFirst())
                                table.addCell(test.getString(0));
                                else
                                    table.addCell(" ");
                            }catch(Exception e){
                                table.addCell(" ");
                            }

                            try{
                                test = database.rawQuery("Select name from subject where registration = '" + sub.get(stf.indexOf(cursor.getString(3))) + "'", null);
                                if(test.moveToFirst())
                                    table.addCell(test.getString(0));
                                else
                                    table.addCell(" ");                            }catch(Exception e){
                                table.addCell(" ");
                            }

                            try{
                                test = database.rawQuery("Select name from subject where registration = '" + sub.get(stf.indexOf(cursor.getString(4))) + "'", null);
                                if(test.moveToFirst())
                                    table.addCell(test.getString(0));
                                else
                                    table.addCell(" ");                            }catch(Exception e){
                                table.addCell(" ");
                            }

                            try{
                                test = database.rawQuery("Select name from subject where registration = '" + sub.get(stf.indexOf(cursor.getString(5))) + "'", null);
                                if(test.moveToFirst())
                                    table.addCell(test.getString(0));
                                else
                                    table.addCell(" ");                            }catch(Exception e){
                                table.addCell(" ");
                            }

                            try{
                                test = database.rawQuery("Select name from subject where registration = '" + sub.get(stf.indexOf(cursor.getString(6))) + "'", null);
                                if(test.moveToFirst())
                                    table.addCell(test.getString(0));
                                else
                                    table.addCell(" ");                            }catch(Exception e){
                                table.addCell(" ");
                            }

                            try{
                                test = database.rawQuery("Select name from subject where registration = '" + sub.get(stf.indexOf(cursor.getString(7))) + "'", null);
                                if(test.moveToFirst())
                                    table.addCell(test.getString(0));
                                else
                                    table.addCell(" ");                            }catch(Exception e){
                                table.addCell(" ");
                            }

                            try{
                                test = database.rawQuery("Select name from subject where registration = '" + sub.get(stf.indexOf(cursor.getString(8))) + "'", null);
                                if(test.moveToFirst())
                                    table.addCell(test.getString(0));
                                else
                                    table.addCell(" ");                            }catch(Exception e){
                                table.addCell(" ");
                            }

                            try{
                                test = database.rawQuery("Select name from subject where registration = '" + sub.get(stf.indexOf(cursor.getString(9))) + "'", null);
                                if(test.moveToFirst())
                                    table.addCell(test.getString(0));
                                else
                                    table.addCell(" ");                            }catch(Exception e){
                                table.addCell(" ");
                            }


                    } while (cursor.moveToNext());
                    cursor.close();
                }


                addEmptyLine(header, 2);


                PdfPTable table2 = new PdfPTable(new float[]{2, 2});
                table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table2.getDefaultCell().setFixedHeight(20);
                table2.addCell("Staff");
                table2.addCell("Subject");

                table2.setHeaderRows(1);

                PdfPCell[] cell = table2.getRow(0).getCells();


                for (int j = 0; j < cell.length; j++) {
                    cell[j].setBackgroundColor(BaseColor.GRAY);
                }

                Cursor c = database.rawQuery("Select * from subject ", null);

                ArrayList<String> sb = new ArrayList<String>(), cd = new ArrayList<String>();

                if(c.moveToFirst()){

                    do{
                        cd.add(c.getString(0));
                        sb.add(c.getString(1));
                    }while (c.moveToNext());
                    c.close();

                }

                cursor = database.rawQuery("Select * from class_staff_table where class_name = '" + cls.getString(0) + "'", null);


                if (cursor.moveToFirst()) {

                    do {

                        table2.addCell(staff_name.get(staff_code.indexOf(cursor.getString(0))));
                        table2.addCell(sb.get(cd.indexOf(cursor.getString(1))));

                    } while (cursor.moveToNext());

                    cursor.close();
                }

                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                document.add(header);
                document.add(table);

                Paragraph p = new Paragraph();
                addEmptyLine(p, 2);
                document.add(p);
                document.add(table2);


                document.close();


            } while (cls.moveToNext());

            cls.close();


        } else {
            cls.close();
        }

        //populate_table("stf2");




        database.close();




    }catch(Exception e){

    }

    }


    public void create_table(){

        try {
            SQLiteDatabase mydatabase = openOrCreateDatabase("TimeTable", MODE_PRIVATE, null);
            mydatabase.execSQL("create table staff(registration varchar(10) primary key not null , name varchar(45))");
            mydatabase.execSQL("create table subject(registration varchar(10) primary key not null , name varchar(45),acronym varchar(45))");
            mydatabase.execSQL("create table class_time_table(name varchar(10),day varchar(5),p1 varchar(45),p2 varchar(45),p3 varchar(45),p4 varchar(45),p5 varchar(45),p6 varchar(45),p7 varchar(45),p8 varchar(45))");
            mydatabase.execSQL("create table class_staff_table(staff_name varchar(45),subject varchar(45),class_name varchar(45))");
            mydatabase.execSQL("create table staff_time_table(name varchar(10),day varchar(5),p1 varchar(45),p2 varchar(45),p3 varchar(45),p4 varchar(45),p5 varchar(45),p6 varchar(45),p7 varchar(45),p8 varchar(45))");
            mydatabase.close();
        }catch(Exception e){return;}

        return;

    }

    public boolean populate(){

        SQLiteDatabase database = openOrCreateDatabase("TimeTable", MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("Select * from staff ",null);

        if(cursor.moveToFirst()){
            do{

                staff_code.add(cursor.getString(0));
                staff_name.add(cursor.getString(1));

            }while(cursor.moveToNext());

            cursor.close();

            return true;

        }else{
            cursor.close();
        }

        Cursor curso = database.rawQuery("Select * from subject ",null);

        if(curso.moveToFirst()){
            do{

                subject_code.add(curso.getString(0));
                subject_name.add(curso.getString(1));
                popup.add(curso.getString(1)+" - "+curso.getString(0));

                Toast.makeText(this, curso.getString(1)+" - "+curso.getString(0), Toast.LENGTH_SHORT).show();

            }while(curso.moveToNext());

            curso.close();
            database.close();

            return true;

        }else{
            cursor.close();
            database.close();
        }

        return false;

    }

    public void showMenu (final View view)
    {
        PopupMenu menu = new PopupMenu (home_activity.this, view);
        menu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener ()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {

                String name = item.getTitle().toString();
                getSupportActionBar().setTitle(name);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                entry_frag fragment = new entry_frag(staff_code.get(staff_name.indexOf(name)),name);
                fragmentTransaction.add(R.id.frame, fragment);
                fragmentTransaction.commit();
                return true;

            }
        });

        for(int i=0;i<staff_name.size();i++) {
            menu.getMenu().add(staff_name.get(i));
        }
        menu.show();

    }


    public boolean activity_populate(){

        SQLiteDatabase database = openOrCreateDatabase("TimeTable",MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("Select * from class_time_table where day = 'Sat'",null);

        if(cursor.moveToFirst()){

            do{

                arrayList.add(cursor.getString(0));
                adap.notifyDataSetChanged();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        arrayList = new ArrayList<String>();
        staff_name = new ArrayList<String>();
        subject_name = new ArrayList<String>();
        staff_code = new ArrayList<String>();
        subject_code = new ArrayList<String>();
        popup = new ArrayList<String>();

        adap = new ArrayAdapter<String>(home_activity.this,android.R.layout.simple_list_item_1,arrayList);

        populate();

        if(activity_populate()){

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            toolbar.setTitle(arrayList.get(0));
            setSupportActionBar(toolbar);


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            home_frag fragment = new home_frag(arrayList.get(0));
            fragmentTransaction.add(R.id.frame, fragment);
            fragmentTransaction.commit();

        }

        TextView empty = (TextView)navigationView.findViewById(R.id.empty_list);

        final ListView class_list = (ListView)navigationView.findViewById(R.id.nav_list);
        class_list.setEmptyView(empty);
        class_list.setAdapter(adap);

        Button edit_staff,edit_subject,staff_table,add_class;

        edit_staff = (Button)navigationView.findViewById(R.id.jump_staff);
        edit_subject = (Button)navigationView.findViewById(R.id.jump_subject);
        staff_table = (Button)navigationView.findViewById(R.id.staff_table);
        add_class = (Button)navigationView.findViewById(R.id.add_class);

        class_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                toolbar.setTitle(arrayList.get(i));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                home_frag fragment = new home_frag(arrayList.get(i));
                fragmentTransaction.add(R.id.frame, fragment);
                fragmentTransaction.commit();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }

        });

        class_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, final View v,
                                           final int index, long arg3) {


                PopupMenu menu = new PopupMenu(home_activity.this, v);

                menu.getMenu().add("Delete");
                menu.show();

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                            SQLiteDatabase database = openOrCreateDatabase("TimeTable", MODE_PRIVATE, null);

                            database.execSQL("delete from class_time_table where name = '" + class_list.getItemAtPosition(index) + "' ");
                            database.execSQL("delete from class_staff_table where class_name = '" + class_list.getItemAtPosition(index) + "' ");

                            database.execSQL("update staff_time_table set p1 = '' where p1 = '" + class_list.getItemAtPosition(index) + "' ");
                            database.execSQL("update staff_time_table set p2 = '' where p2 = '" + class_list.getItemAtPosition(index) + "' ");
                            database.execSQL("update staff_time_table set p3 = '' where p3 = '" + class_list.getItemAtPosition(index) + "' ");
                            database.execSQL("update staff_time_table set p4 = '' where p4 = '" + class_list.getItemAtPosition(index) + "' ");
                            database.execSQL("update staff_time_table set p5 = '' where p5 = '" + class_list.getItemAtPosition(index) + "' ");
                            database.execSQL("update staff_time_table set p6 = '' where p6 = '" + class_list.getItemAtPosition(index) + "' ");
                            database.execSQL("update staff_time_table set p7 = '' where p7 = '" + class_list.getItemAtPosition(index) + "' ");
                            database.execSQL("update staff_time_table set p8 = '' where p8 = '" + class_list.getItemAtPosition(index) + "' ");

                            database.close();
                            finish();

                            startActivity(getIntent());

                        return true;
                    }
                });

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }


        });


        edit_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(home_activity.this,entry_staff.class));

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        edit_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(home_activity.this,entry_subject.class));

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        staff_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showMenu(view);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(home_activity.this,class_entry.class));

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

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
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onRestart(){
        super.onRestart();

        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.generate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_name) {


            try {
                generate();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            }

            Toast.makeText(this, "Generate click", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
