package com.hazelwood.labfour;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class Control_Fragment extends Fragment implements ServiceConnection {


    Music_Service.Music_Binder binder;
    int current_song = 0;

    public static final String TAG = "Control_FragmentTAG";
    private OnFragmentInteractionListener mListener;

    public static Control_Fragment newInstance() {
        Control_Fragment fragment = new Control_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    public Control_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.control_fragment, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        Intent service = new Intent(getActivity(), Music_Service.class);
        getActivity().startService(service);
        getActivity().bindService(service, this, Context.BIND_AUTO_CREATE);

        ImageButton previous = (ImageButton) getActivity().findViewById(R.id.previousBTN);
        ImageButton pause = (ImageButton) getActivity().findViewById(R.id.pauseBTN);
        ImageButton play = (ImageButton) getActivity().findViewById(R.id.playBTN);
        ImageButton next = (ImageButton) getActivity().findViewById(R.id.nextBTN);
        Button stop = (Button) getActivity().findViewById(R.id.stopBTN);

        previous.setOnClickListener(onClickListener);
        pause.setOnClickListener(onClickListener);
        play.setOnClickListener(onClickListener);
        next.setOnClickListener(onClickListener);
        stop.setOnClickListener(onClickListener);

    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Music_Service music_service = binder.getService();
            int id = view.getId();

            switch (id){
                case R.id.playBTN:
                    music_service.play();
//                    SeekBar seekBar = (SeekBar) getActivity().findViewById(R.id.seekBAR);
//                    seekBar.setMax(music_service.getDuration());
//                    seekBar.setProgress(music_service.getPosition());
                    Log.d(TAG, String.valueOf(music_service.getDuration()));
                    Log.d(TAG, String.valueOf(music_service.getPosition()));
                    break;
                case R.id.previousBTN:
                    music_service.previous();
                    break;
                case R.id.pauseBTN:
                    music_service.pause();
                    break;
                case R.id.nextBTN:
                    music_service.next();
                    break;
                case R.id.stopBTN:
                    music_service.stop();
                    break;
                default:
                    break;
            }

        }
    };


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        binder = (Music_Service.Music_Binder)iBinder;
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
