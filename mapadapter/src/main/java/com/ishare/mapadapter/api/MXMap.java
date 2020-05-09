package com.ishare.mapadapter.api;

import android.view.View;

import com.ishare.mapadapter.MapConstant;
import com.ishare.mapadapter.bean.MXLatLng;
import com.ishare.mapadapter.bean.MXMarker;
import com.ishare.mapadapter.bean.MXPolyline;

import java.util.List;

public interface MXMap {

    void createMap(View view, MXMap.OnMapReadyCallback onMapReadyCallback);

    void createMap(android.app.Fragment fragment, MXMap.OnMapReadyCallback onMapReadyCallback);

    void createMap(android.support.v4.app.Fragment fragment, OnMapReadyCallback onMapReadyCallback);

    void uiSetting();

    void setLoadOfflineData(boolean enabled);

    void zoomIn(int duration);

    void zoomOut(int duration);

    void zoomBy(float level, int duration);

    void animateCamera(MXLatLng mxLatLng, float level, int duration);

    void animateCamera(List<MXLatLng> mxLatLngList, int duration);

    String addMarker(MXMarker mxMarker);

    String addMarker(MXMarker mxMarker, boolean isAnimate);

    List<String> addMarkers(List<MXMarker> mxMarkerList);

    void updateMarker(MXMarker mxMarker);

    void removeMarker(MXMarker mxMarker);

    void addPolyline(MXPolyline mxPolyline);

    void removePolyline(MXPolyline mxPolyline);

    void clear();

    void setMapType(MapConstant.MapType mapType);

    void setOnMarkerClickListener(OnMarkerClickListener markerClickListener);

    void setOnCameraChangeListener(OnCameraChangeListener onCameraChangeListener);

    interface OnMapReadyCallback {
        void onMapReady();
    }

    interface OnMarkerClickListener {
        boolean onMarkerClick(String markerId);
    }

    interface OnCameraChangeListener {
        void onCameraChange(float zoom, double latitude, double longitude);

        void onCameraChangeFinish(float zoom, double latitude, double longitude);
    }
}
