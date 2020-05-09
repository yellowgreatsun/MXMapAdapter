package com.ishare.mapadapter.googlemapimpl;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.MapView;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.api.MXMapView;

public class GoogleMapViewImpl implements MXMapView {

    private MapView mapView;

    @Override
    public View createMapView(Context context) {

        mapView = new MapView(context);
        return mapView;
    }

    @Override
    public void createMap(MXMapView.OnMapReadyCallback onMapReadyCallback) {

        MXMap aMapManager = new GoogleMapImpl();
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
