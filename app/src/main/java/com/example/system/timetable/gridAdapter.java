package com.example.system.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public class gridAdapter extends BaseAdapter{


    Context mContext;
    ArrayList<String> list = new ArrayList<String>();

    public gridAdapter(Context c,ArrayList<String> list){

        mContext = c;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return list.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v;
        if(i == list.size()-1)
         v = inflater.inflate(R.layout.new_class,null);
        else
         v = inflater.inflate(R.layout.class_grid,null);

        return v;


    }
}
