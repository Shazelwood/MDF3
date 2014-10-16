package com.hazelwood.widgetlistlab.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Hazelwood on 10/16/14.
 */
public class List_Service extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new List_Factory(getApplicationContext());
    }

}
