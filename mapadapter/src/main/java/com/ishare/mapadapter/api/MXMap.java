package com.ishare.mapadapter.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.ishare.mapadapter.MapConstant;
import com.ishare.mapadapter.bean.MXLatLng;
import com.ishare.mapadapter.bean.MXMapScale;
import com.ishare.mapadapter.bean.MXMarker;
import com.ishare.mapadapter.bean.MXPolyline;
import com.ishare.mapadapter.indoor.MapIndoorInfo;

import java.util.List;

public interface MXMap {

    void createMap(View view, OnMapReadyCallback onMapReadyCallback);

    void createMap(android.app.Fragment fragment, OnMapReadyCallback onMapReadyCallback);

    void createMap(android.support.v4.app.Fragment fragment, OnMapReadyCallback onMapReadyCallback);

    void uiSetting(Context context);

    void showMyLocation(Context context, boolean enable, Bitmap icon);

    void setLoadOfflineData(boolean enabled);

    void zoomIn(int duration);

    void zoomOut(int duration);

    void zoomBy(float level, int duration);

    void animateCamera(MXLatLng mxLatLng, float level, int duration);

    void animateCamera(List<MXLatLng> mxLatLngList, int duration);

    String addMarker(Context context, MXMarker mxMarker);

    String addMarker(Context context, MXMarker mxMarker, boolean isAnimate);

    List<String> addMarkers(Context context, List<MXMarker> mxMarkerList);

    void updateMarker(Context context, MXMarker mxMarker);

    void updateMarker(Context context, MXMarker mxMarker, boolean isAnimate);

    void removeMarker(MXMarker mxMarker);

    void addPolyline(MXPolyline mxPolyline);

    void removePolyline(MXPolyline mxPolyline);

    void clear();

    void setMapType(MapConstant.MapType mapType);

    MXMapScale getMapScaleParams(float zoom);

    void setIndoorMapFloor(MapIndoorInfo mapIndoorInfo);

    void setOnMapClickListener(OnMapClickListener mapClickListener);

    void setOnMarkerClickListener(OnMarkerClickListener markerClickListener);

    void setOnCameraChangeListener(OnCameraChangeListener onCameraChangeListener);

    void setOnIndoorMapListener(OnIndoorMapListener onIndoorMapListener);

    interface OnMapReadyCallback {
        void onMapReady();
    }

    interface OnMapClickListener {
        void onMapClick();
    }

    interface OnMarkerClickListener {
        boolean onMarkerClick(String markerId);
    }

    interface OnCameraChangeListener {
        void onCameraChange(float zoom, double latitude, double longitude);

        void onCameraChangeFinish(float zoom, double latitude, double longitude);
    }

    interface OnIndoorMapListener{
        void onIndoormapInfo(boolean isIndoor, MapIndoorInfo mapIndoorInfo);
    }
}
