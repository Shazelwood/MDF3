package com.hazelwood.labthree;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Hazelwood on 10/4/14.
 */
public class List_Fragment extends Fragment {
    public static final String TAG = "List_FragmentTAG";
    ArrayList<NYTimes> nyTimesArrayList = new ArrayList<NYTimes>();

    public interface List_FragmentListener{
        public void populateDetail();
    }

    public List_FragmentListener mListener;

    public List_Fragment(){

    }

    public static List_Fragment newInstance(NYTimes object){
        List_Fragment fragment = new List_Fragment();
        if (object == null){
            Log.d(TAG, "There's no data");
        }else {
            Log.d(TAG, "There's data");

            Bundle args = new Bundle();
            args.putSerializable("OBJECT", object);
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof List_FragmentListener){
            mListener = (List_FragmentListener) activity;
        } else {
            throw new IllegalArgumentException("");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null){

        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View frag = inflater.inflate(R.layout.list_frag, container, false);
        return frag;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();

        NYTimes object = (NYTimes) args.getSerializable("OBJECT");
        nyTimesArrayList.add(object);
        Log.d(TAG, object.getTitle());


        NYTimesAdapter adapter = new NYTimesAdapter(getActivity(), nyTimesArrayList);
        ListView listView = (ListView)getActivity().findViewById(R.id.list_frag);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(nyTimesArrayList.get(i).getNewsURL()));
                startActivity(intent);
            }
        });


    }
}
