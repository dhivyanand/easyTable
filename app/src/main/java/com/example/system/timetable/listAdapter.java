package com.example.system.timetable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class listAdapter<string> extends ArrayAdapter<String> {

    Context context;
    int resource;
    ArrayList data;

    public listAdapter(@NonNull Context context, int resource , ArrayList data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tv);
        textView.setText(data.get(position).toString());

        return rowView;
    }
}
