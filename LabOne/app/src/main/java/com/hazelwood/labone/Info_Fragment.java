package com.hazelwood.labone;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Hazelwood on 9/30/14.
 */
public class Info_Fragment extends Fragment{
    public static final String TAG = "Info_FragmentTAG";
    static final String ACTION_SAVED_DATA = "com.fullsail.android.ACTION_SAVE_DATA";

    public interface Info_FragmentListener{
        public void getForm();
    }

    public Info_FragmentListener mListener;

    public Info_Fragment(){

    }

    public static Info_Fragment newInstance(){
        Info_Fragment fragment = new Info_Fragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Info_FragmentListener){
            mListener = (Info_FragmentListener) activity;
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
        View fragView = inflater.inflate(R.layout.fragment_info_form, container, false);
        return fragView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button = (Button) getActivity().findViewById(R.id.send_formBTN);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Info> arrayList = new ArrayList<Info>();

                EditText et1 = (EditText) getActivity().findViewById(R.id.firstnameET_frag);
                EditText et2 = (EditText) getActivity().findViewById(R.id.lastnameET_frag);
                EditText et3 = (EditText) getActivity().findViewById(R.id.ageET_frag);

                if (et1.getText().length() != 0 ||et1.getText().length() != 0 ||et1.getText().length() != 0){
                    final int num = Integer.parseInt(String.valueOf(et3.getText()));
                    arrayList.add(new Info(et1.getText().toString(), et2.getText().toString(), num));
                    Intent intent;
                    intent = new Intent(ACTION_SAVED_DATA);
                    intent.putExtra("com.fullsail.android.EXTRA_FIRST_NAME",et1.getText().toString());
                    intent.putExtra("com.fullsail.android.EXTRA_LAST_NAME",et2.getText().toString());
                    intent.putExtra("com.fullsail.android.EXTRA_AGE",num);
                    getActivity().sendBroadcast(intent);
                    getActivity().getFragmentManager().popBackStack();
                }



            }
        });
    }

}
