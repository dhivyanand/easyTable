package com.example.system.timetable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    public String[] a;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.pp,R.mipmap.ic_launcher
    };

    String [] row = {"","1","2","3","4","5","6","7","8"};
    String [] col = {"","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    // Constructor
    public ImageAdapter(Context c,String[] a){
        mContext = c;
        this.a = a;
    }

    @Override
    public int getCount() {
        return 63;
    }

    @Override
    public Object getItem(int position) {
        return "";
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(position == 0){

            View v = inflater.inflate(R.layout.layout_logo,null);
            return v;

        }

        if(position < 9 || position % 9 == 0){

            View v = inflater.inflate(R.layout.layout_week,null);

            TextView t = v.findViewById(R.id.tv1);

            if(position < 9)
               t.setText(row[position]);
            else
               t.setText(col[position/9]);

            return v;

        }else if(((position>18) && (position<27)) || ((position>36) && (position<45)) || (position>54)){

            View v = inflater.inflate(R.layout.activity_main,null);

            TextView t = v.findViewById(R.id.tv1);

            t.setText(a[position]);

            return v;

        }else{

            View v = inflater.inflate(R.layout.layout_week_alternative,null);

            TextView t = v.findViewById(R.id.tv1);

            t.setText(a[position]);

            return v;

        }




    }

}
