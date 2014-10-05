package com.hazelwood.labtwo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.File;

/**
 * Created by Hazelwood on 10/2/14.
 */
public class Grid_Adapter extends BaseAdapter {
    private Context mContext;
    File[] mObjects;

    public Grid_Adapter(Context c, String[] objects){
        mContext = c;


    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
