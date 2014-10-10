package com.hazelwood.maps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Hazelwood on 10/9/14.
 */
public class Map_Fragment extends MapFragment implements LocationListener {
    public static final String TAG = "MAPFRAGMENT_TAG";
    GoogleMap map;
    LocationManager locationManager;
    Location location;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onLocationChanged(Location location) {
        mListener.onFragmentInteraction(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {


    }

    @Override
    public void onProviderEnabled(String s) {
        if (s.equals(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(s, 5000, 10, this);
        }
    }

    @Override
    public void onProviderDisabled(String s) {
        if (s.equals(LocationManager.GPS_PROVIDER)){
            locationManager.removeUpdates(this);
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(double lat, double lng);
    }

    public static Map_Fragment newInstance() {
        Map_Fragment fragment = new Map_Fragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }
    public Map_Fragment() {
        // Required empty public constructor
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null){
            Log.d(TAG, "no new field");
        }


//        CameraUpdateFactory cameraUpdateFactory
//        setHasOptionsMenu(true);

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        getActivity().getMenuInflater().inflate(R.menu.main, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_add) {
//            Intent form = new Intent(getActivity(), Form_Activity.class);
//            startActivity(form);
//
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Form> arrayList = new ArrayList<Form>();

        try {
            File extFolder = getActivity().getExternalFilesDir(null);
            File file = new File(extFolder, "MAP.dat");

            if (file.exists()) {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream oin = new ObjectInputStream(fin);
                arrayList = (ArrayList<Form>) oin.readObject();
                oin.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        Log.d(TAG, String.valueOf(arrayList.size()));
        map = getMap();
        map.setMyLocationEnabled(true);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));

//        map.set


        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Intent form = new Intent(getActivity(), Form_Activity.class);
                form.putExtra("LAT", latLng.latitude);
                form.putExtra("LNG", latLng.longitude);
                startActivity(form);
            }
        });

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getActivity().getLayoutInflater().inflate(R.layout.window_info, null);
                TextView textView =(TextView) v.findViewById(R.id.info_fieldONE);
                textView.setText(marker.getTitle());

                TextView textView2 = (TextView) v.findViewById(R.id.info_fieldTWO);
                textView2.setText(marker.getSnippet());


                return v;
            }
        });

        if (arrayList != null){
            for (int i = 0; i < arrayList.size(); i++){
                Form form = arrayList.get(i);
                map.addMarker(new MarkerOptions().position(new LatLng(form.getLatitude(), form.getLongitude())).title(form.getFieldONE()).snippet(form.getFieldTWO()));
            }
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
