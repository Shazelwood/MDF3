package com.hazelwood.labone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hazelwood on 9/30/14.
 */
public class Info_Adapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Info> mObjects;

    private static final long ID_CONSTANT = 123456789;

    public Info_Adapter(Context c, ArrayList<Info> objects){
        mContext = c;
        mObjects = objects;
    }


    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Info getItem(int i) {
        return mObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (ID_CONSTANT + i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.list_custom_row, viewGroup, false);
        }

        Info item = getItem(i);
        TextView tv1 = (TextView)view.findViewById(R.id.fNameTV);
        TextView tv2 = (TextView)view.findViewById(R.id.lNameTV);
        TextView tv3 = (TextView)view.findViewById(R.id.ageTV);

        tv1.setText(item.getFname());
        tv2.setText(item.getLname());
        tv3.setText(Integer.toString(item.getAge()));
        return view;
    }
}
