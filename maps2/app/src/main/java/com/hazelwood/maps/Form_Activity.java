package com.hazelwood.maps;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Form_Activity extends Activity implements Form_Fragment.OnFragmentInteractionListener{
    public static final String TAG = "FORMACTIVITY_TAG";
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Intent info = getIntent();
        latitude = info.getDoubleExtra("LAT", -1);
        longitude = info.getDoubleExtra("LNG", -1);

        getFragmentManager().beginTransaction().replace(R.id.form_container, Form_Fragment.newInstance(latitude, longitude)).commit();


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
