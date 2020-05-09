package com.ishare.mapadapter.googlemapimpl;

import android.app.Fragment;
import android.content.Context;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.api.MXMapFragment;

public class GoogleMapFragmentImpl implements MXMapFragment {

    private MapFragment mapFragment;
    private SupportMapFragment supportMapFragment;

    @Override
    public Fragment createMapFragment(Context context) {

        mapFragment = new MapFragment();
        return mapFragment;
    }

    @Override
    public android.support.v4.app.Fragment createSupportMapFragment(Context context) {

        supportMapFragment = new SupportMapFragment();
        return supportMapFragment;
    }

    @Override
    public void createMap(MXMapFragment.OnMapReadyCallback onMapReadyCallback) {

        MXMap aMapManager = new GoogleMapImpl();
        if (supportMapFragment != null) {
            aMapManager.createMap(supportMapFragment, new MXMap.OnMapReadyCallback() {
                @Override
                public void onMapReady() {
                    onMapReadyCallback.onMapReady(aMapManager);
                }
            });
        } else if (mapFragment != null) {
            aMapManager.createMap(mapFragment, new MXMap.OnMapReadyCallback() {
                @Override
                public void onMapReady() {
                    onMapReadyCallback.onMapReady(aMapManager);
                }
            });
        }
    }
}
