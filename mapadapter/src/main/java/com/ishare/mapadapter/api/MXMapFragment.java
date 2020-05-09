package com.ishare.mapadapter.api;

import android.content.Context;

public interface MXMapFragment {

    android.app.Fragment createMapFragment(Context context);

    android.support.v4.app.Fragment createSupportMapFragment(Context context);

    void createMap(OnMapReadyCallback onMapReadyCallback);

    interface OnMapReadyCallback {
        void onMapReady(MXMap mxMap);
    }
}
