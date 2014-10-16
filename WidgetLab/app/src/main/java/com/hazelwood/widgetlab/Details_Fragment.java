package com.hazelwood.widgetlab;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Hazelwood on 10/15/14.
 */
public class Details_Fragment extends Fragment {
    public static final String TAG = "DetailsFragmentTAG";

    private DetailsFragmentListener mListener;

    public interface DetailsFragmentListener{
        public void populateDetails(Weather weather);
    }

    public static Details_Fragment newInstance(Weather data){
        Details_Fragment fragment = new Details_Fragment();
        if (data == null){
            Log.d(TAG, "No data");

        } else {
            Bundle args = new Bundle();
            args.putSerializable("data", data);

            fragment.setArguments(args);
        }

        return fragment;
    }



    public Details_Fragment(){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DetailsFragmentListener){
            mListener = (DetailsFragmentListener) activity;
        } else {
            throw new IllegalArgumentException("Attaching class must implement DetailsFragmentListener");
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
        View fragView = inflater.inflate(R.layout.detail_frag, container, false);

        return fragView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle arg = getArguments();
        Log.d(TAG, "Arguments: "  + arg.toString());
        if (arg != null){
            Log.d(TAG, "ARGUEMENTS");

            Weather weather = (Weather) arg.getSerializable("data");

            TextView tv1 = (TextView) getActivity().findViewById(R.id.conditionTV);
            TextView tv2 = (TextView) getActivity().findViewById(R.id.temperatureTV);
            TextView tv3 = (TextView) getActivity().findViewById(R.id.high_lowTV);

            TextView tv4 = (TextView) getActivity().findViewById(R.id.tomorrowTV);
            TextView tv5 = (TextView) getActivity().findViewById(R.id.YesterdayTV);

            tv1.setText(weather.getConditions());
            tv2.setText(String.valueOf(weather.getTemperatureNOW()));
            tv3.setText(weather.getTempHigh() + "/" + weather.getTempLow());
            tv4.setText(String.valueOf(weather.getTemperatureTOMORROW()));
            tv5.setText(String.valueOf(weather.getTemperatureYESTERDAY()));
        }

    }
}
