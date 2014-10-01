package com.hazelwood.labone;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Hazelwood on 9/30/14.
 */
public class Detail_Fragment extends Fragment {
    Intent intent;
    public static final String TAG = "DetailfragmentTAG";
    static final String ACTION_DELETE_DATA = "com.fullsail.android.ACTION_DELETE_DATA";
    static HashMap<String, String> hashMap;

    public interface Detail_FragmentListener{
        public void populateDetail();
    }

    public Detail_FragmentListener mListener;

    public Detail_Fragment(){

    }

    public static Detail_Fragment newInstance(String first_name, String last_name, int _age, int _pos){
        hashMap = new HashMap<String, String>();
        hashMap.put("FIRSTNAME", first_name);
        hashMap.put("LASTNAME", last_name);
        hashMap.put("AGE", String.valueOf(_age));
        hashMap.put("POSITION", String.valueOf(_pos));
        Detail_Fragment fragment = new Detail_Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("HASH_MAP", hashMap);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Detail_FragmentListener){
            mListener = (Detail_FragmentListener) activity;
        } else {
            throw new IllegalArgumentException("");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:

                getActivity().sendBroadcast(intent);
                getActivity().getFragmentManager().popBackStack();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragView = inflater.inflate(R.layout.fragment_detail, container, false);
        return fragView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String nameFIRST = hashMap.get("FIRSTNAME");
        String nameLAST = hashMap.get("LASTNAME");
        String stringAGE = hashMap.get("AGE");
        int stringPosition = Integer.parseInt(hashMap.get("POSITION"));

        intent = new Intent(ACTION_DELETE_DATA);

        intent.putExtra("FIRST_NAME", nameFIRST);
        intent.putExtra("LAST_NAME", nameLAST);
        intent.putExtra("PERSON_AGE", stringAGE);
        intent.putExtra("PERSON_POSITION", stringPosition);

//        Log.d(TAG, stringPosition);

        TextView tvONE = (TextView) getActivity().findViewById(R.id.detailFIRSTNAME);
        TextView tvTWO = (TextView) getActivity().findViewById(R.id.detailLASTNAME);
        TextView tvTHREE = (TextView) getActivity().findViewById(R.id.detailAGE);

        tvONE.setText(nameFIRST);
        tvTWO.setText(nameLAST);
        tvTHREE.setText(stringAGE);
    }
}
