package com.ishare.mapadapter.bdmapimpl;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.MapView;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.api.MXMapView;

public class BDMapViewImpl implements MXMapView {

    private MapView mapView;

    @Override
    public View createMapView(Context context) {

        mapView = new MapView(context);
        return mapView;
    }

    @Override
    public void createMap(final MXMapView.OnMapReadyCallback onMapReadyCallback) {

        final MXMap aMapManager = new BDMapImpl();
        aMapManager.createMap(mapView, () -> onMapReadyCallback.onMapReady(aMapManager));
    }

    @Override
    public void onCreate(Context context, Bundle bundle) {
        mapView.onCreate(context, bundle);
    }

    @Override
    public void onResume() {
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        mapView.onSaveInstanceState(bundle);
    }
}
