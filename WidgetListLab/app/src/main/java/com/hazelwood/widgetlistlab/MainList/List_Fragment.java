package com.hazelwood.widgetlistlab.MainList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hazelwood.widgetlistlab.Details.Detail_Activity;
import com.hazelwood.widgetlistlab.NewsArticle;
import com.hazelwood.widgetlistlab.R;

import java.util.ArrayList;

/**
 * Created by Hazelwood on 9/8/14.
 */
public class List_Fragment extends Fragment {
    ListView detailsList;

    public static final String TAG = "DetailsFragmentTAG";

    private ListFragmentListener mListener;

    public interface ListFragmentListener{
//        public void populateDetails(ArrayList<NYTimes> array);
    }

    public static List_Fragment newInstance(ArrayList<NewsArticle> data){
        List_Fragment fragment = new List_Fragment();
        if (data == null){
            Log.d(TAG, "No data");
        } else {
            Bundle args = new Bundle();
            args.putSerializable("data", data);

            fragment.setArguments(args);
        }

        return fragment;
    }



    public List_Fragment(){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ListFragmentListener){
            mListener = (ListFragmentListener) activity;
        } else {
            throw new IllegalArgumentException("Attaching class must implement ListFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.list_frag, container, false);

        return fragView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle arg = getArguments();
        if (arg != null){
            Log.d(TAG, "ARGUEMENTS");

            final ArrayList<NewsArticle> articles = (ArrayList<NewsArticle>) arg.getSerializable("data");
            detailsList = (ListView)getActivity().findViewById(R.id.list_frag_view);

            Article_Adapter adapter = new Article_Adapter(getActivity(), articles);
            detailsList.setAdapter(adapter);

            detailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent detailIntent = new Intent(getActivity(), Detail_Activity.class);
                    detailIntent.putExtra(Detail_Activity.EXTRA_ITEM, articles.get(i));
                    startActivity(detailIntent);
                }
            });


        }

    }
}
