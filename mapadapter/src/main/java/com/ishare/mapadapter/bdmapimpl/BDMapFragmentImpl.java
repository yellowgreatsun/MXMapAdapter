package com.ishare.mapadapter.bdmapimpl;

import android.app.Fragment;
import android.content.Context;

import com.baidu.mapapi.map.MapFragment;
import com.baidu.mapapi.map.SupportMapFragment;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.api.MXMapFragment;

public class BDMapFragmentImpl implements MXMapFragment {

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
    public void createMap(final OnMapReadyCallback onMapReadyCallback) {

        final MXMap bdMapManager = new BDMapImpl();
        if (supportMapFragment != null) {
            bdMapManager.createMap(supportMapFragment, () -> onMapReadyCallback.onMapReady(bdMapManager));
        } else if (mapFragment != null) {
            bdMapManager.createMap(mapFragment, () -> onMapReadyCallback.onMapReady(bdMapManager));
        }
    }
}
