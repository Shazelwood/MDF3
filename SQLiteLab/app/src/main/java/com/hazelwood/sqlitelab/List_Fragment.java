package com.hazelwood.sqlitelab;

import android.app.ListFragment;
import android.os.Bundle;

/**
 * Created by Hazelwood on 10/21/14.
 */
public class List_Fragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DataHelper helper = new DataHelper(getActivity());



//        List_Adapter adapter = new List_Adapter(getActivity(), cursor, 0);
//        setListAdapter(adapter);

//        List_Adapter adapter = new List_Adapter();
//        setListAdapter();
    }

}
