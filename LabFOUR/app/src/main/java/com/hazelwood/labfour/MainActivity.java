package com.hazelwood.labfour;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements Info_Fragment.OnFragmentInteractionListener, Control_Fragment.OnFragmentInteractionListener {

    FragmentManager mFragManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragManager = getFragmentManager();

        mFragManager.beginTransaction().replace(R.id.containerTOP, Info_Fragment.newInstance(), Info_Fragment.TAG).commit();
        mFragManager.beginTransaction().replace(R.id.containerBOTTOM, Control_Fragment.newInstance(), Control_Fragment.TAG).commit();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
