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
    public void createMap(OnMapReadyCallback onMapReadyCallback) {

        MXMap aMapManager = new BDMapImpl();
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
