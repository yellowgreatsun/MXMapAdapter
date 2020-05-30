package com.ishare.mapadapter.amapimpl;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.MapView;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.api.MXMapView;

public class AMapViewImpl implements MXMapView {

    private MapView mapView;

    @Override
    public View createMapView(Context context) {

        mapView = new MapView(context);
        return mapView;
    }

    @Override
    public void createMap(final MXMapView.OnMapReadyCallback onMapReadyCallback) {

        final MXMap aMapManager = new AMapImpl();
        aMapManager.createMap(mapView, () -> onMapReadyCallback.onMapReady(aMapManager));
    }

    @Override
    public void onCreate(Context context, Bundle bundle) {
        mapView.onCreate(bundle);
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
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        mapView.onSaveInstanceState(bundle);
    }
}
