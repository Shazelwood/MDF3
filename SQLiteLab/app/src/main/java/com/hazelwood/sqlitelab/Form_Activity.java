package com.hazelwood.sqlitelab;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by Hazelwood on 10/21/14.
 */
public class Form_Activity extends Activity implements Form_Fragment.OnFragmentInteractionListener {

    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mFragmentManager = getFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.form_container, Form_Fragment.newInstance(12, 12), Form_Fragment.TAG).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
