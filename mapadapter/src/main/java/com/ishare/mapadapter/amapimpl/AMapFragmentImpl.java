package com.ishare.mapadapter.amapimpl;

import android.content.Context;

import com.amap.api.maps.MapFragment;
import com.amap.api.maps.SupportMapFragment;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.api.MXMapFragment;

public class AMapFragmentImpl implements MXMapFragment {

    private MapFragment mapFragment;
    private SupportMapFragment supportMapFragment;

    @Override
    public android.app.Fragment createMapFragment(Context context) {

        mapFragment = new MapFragment();
        return mapFragment;
    }

    @Override
    public android.support.v4.app.Fragment createSupportMapFragment(Context context) {

        supportMapFragment = new SupportMapFragment();
        return supportMapFragment;
    }

    @Override
    public void createMap(final MXMapFragment.OnMapReadyCallback onMapReadyCallback) {

        final MXMap aMapManager = new AMapImpl();
        if (supportMapFragment != null) {
            aMapManager.createMap(supportMapFragment, () -> onMapReadyCallback.onMapReady(aMapManager));
        } else if (mapFragment != null) {
            aMapManager.createMap(mapFragment, () -> onMapReadyCallback.onMapReady(aMapManager));
        }
    }
}
