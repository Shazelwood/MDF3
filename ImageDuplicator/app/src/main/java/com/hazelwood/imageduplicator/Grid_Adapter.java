package com.hazelwood.imageduplicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Hazelwood on 10/11/14.
 */
public class Grid_Adapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mObjects;

    private static final long ID_CONSTANT = 123456789;

    public Grid_Adapter(Context c, ArrayList<String> objects){
        mContext = c;
        mObjects = objects;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public String getItem(int i) {
        return mObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ID_CONSTANT + i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.custom_grid_cell, viewGroup, false);
        }

        ImageView imageView = (ImageView)view.findViewById(R.id.grid_image);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 10;
        Bitmap image = BitmapFactory.decodeFile(getItem(i), options);

        imageView.setImageBitmap(image);

        return view;
    }
}
