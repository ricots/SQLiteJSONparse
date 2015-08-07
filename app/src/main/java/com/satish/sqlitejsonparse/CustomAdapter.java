package com.satish.sqlitejsonparse;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by satish on 2/7/15.
 */
public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    LayoutInflater inflater;
    private ArrayList<ListDataModel> listdata;
    public CustomAdapter(ArrayList<ListDataModel> listdata, Activity activity) {
        this.listdata=listdata;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(inflater==null)
            inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            view =inflater.inflate(R.layout.list_view, null);
        TextView id= (TextView) view.findViewById(R.id.id);
        TextView name= (TextView) view.findViewById(R.id.name);
        TextView email= (TextView) view.findViewById(R.id.email);
        TextView phone= (TextView) view.findViewById(R.id.phone);
        ListDataModel data=listdata.get(position);
        id.setText(data.getId());
        name.setText(data.getName());
        email.setText(data.getEmail());
        phone.setText(data.getPhone());
        return view;
    }
}
