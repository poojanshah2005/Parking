package com.poojanshah.json_fist_application;

import android.app.Activity;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Poojan on 22/07/2017.
 */

public class details extends Activity implements GoogleMap.InfoWindowAdapter {

    public details() {
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
