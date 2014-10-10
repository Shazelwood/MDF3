package com.hazelwood.maps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements Map_Fragment.OnFragmentInteractionListener {
    double latitude, longitude;
    public static final String TAG = "MAINACTIVTY_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.container, Map_Fragment.newInstance()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent form = new Intent(this, Form_Activity.class);
            form.putExtra("LAT", latitude);
            form.putExtra("LNG", longitude);
            startActivity(form);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(double lat, double lng) {
        latitude = lat;
        longitude = lng;

        Log.d(TAG, String.valueOf(latitude) + " " + String.valueOf(longitude));

    }
}
