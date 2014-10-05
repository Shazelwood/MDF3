package com.hazelwood.labtwo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.File;

/**
 * Created by Hazelwood on 10/2/14.
 */
public class Grid_Fragment extends Fragment {
    public static final String TAG = "Grid_FragmentTAG";
    public static final String BYTE_KEY = "IMAGE_BYTES";

    public interface Grid_FragmentListener{

    }

    public Grid_FragmentListener mListener;

    public Grid_Fragment(){

    }

    public static Grid_Fragment newInstance(File[] images){
        Grid_Fragment fragment = new Grid_Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BYTE_KEY, images);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Grid_FragmentListener){
            mListener = (Grid_FragmentListener) activity;
        } else {
            throw new IllegalArgumentException("");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gridview_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridView gridView = (GridView) getActivity().findViewById(R.id.gridview_frag);

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), Grid_Detail_Activity.class);
//                startActivity(intent);
//
//            }
//        });



    }
}
