package com.hazelwood.labone;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Hazelwood on 9/30/14.
 */
public class List_Fragment extends Fragment implements Detail_Fragment.Detail_FragmentListener {
    public static final String TAG = "List_FragmentTAG";

    @Override
    public void populateDetail() {

    }

    public interface List_FragmentListener{
        public void populateDetail(String fn, String ln, int _age, int position);
    }

    public List_FragmentListener mListener;

    public List_Fragment(){

    }

    public static List_Fragment newInstance(ArrayList<Info> info){
        List_Fragment fragment = new List_Fragment();
        if (info == null){
            Log.d(TAG, "There's no data");
        }else {
            Log.d(TAG, "There's data");

            Bundle args = new Bundle();
            args.putSerializable("info", info);
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
        View fragView = inflater.inflate(R.layout.fragment_list, container, false);
        return fragView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = (ListView) getActivity().findViewById(R.id.listView_frag);

        Bundle arg = getArguments();

        if (arg != null){
            Log.d(TAG, "Arguments exists");
            ArrayList<Info> infoArrayList = (ArrayList<Info>) arg.getSerializable("info");
            Info_Adapter adapter = new Info_Adapter(getActivity(), infoArrayList);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Info item = (Info) adapterView.getAdapter().getItem(i);
                mListener.populateDetail(item.getFname(),item.getLname(), item.getAge(), i);

            }
        });

    }
}
