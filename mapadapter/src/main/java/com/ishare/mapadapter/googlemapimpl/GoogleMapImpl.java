package com.ishare.mapadapter.googlemapimpl;

import android.app.Fragment;
import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ishare.mapadapter.MapConstant;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.bean.MXLatLng;
import com.ishare.mapadapter.bean.MXMarker;
import com.ishare.mapadapter.bean.MXPolyline;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapImpl implements MXMap {

    private GoogleMap mGoogleMap;

    /**
     * Application onCreate调用
     *
     * @param context 传入ApplicationContext
     */
    public static void initAppMapManager(Context context) {

    }

    /**
     * Application onCreate调用
     *
     * @param context   传入ApplicationContext
     * @param sdcardDir sdcard路径
     */
    public static void initAppMapManager(Context context, String sdcardDir) {

    }

    /**
     * Activity onCreate调用
     *
     * @param context 传入ApplicationContext
     */
    public static void initActivityMapManager(Context context, String sdcardDir) {

    }

    @Override
    public void createMap(View view, MXMap.OnMapReadyCallback onMapReadyCallback) {

        MapView mapView = (MapView) view;
        mapView.getMapAsync(new com.google.android.gms.maps.OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                onMapReadyCallback.onMapReady();
            }
        });
    }

    @Override
    public void createMap(Fragment fragment, MXMap.OnMapReadyCallback onMapReadyCallback) {

        MapFragment mapFragment = (MapFragment) fragment;
        mapFragment.getMapAsync(new com.google.android.gms.maps.OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                onMapReadyCallback.onMapReady();
            }
        });
    }

    @Override
    public void createMap(android.support.v4.app.Fragment fragment, MXMap.OnMapReadyCallback onMapReadyCallback) {

        SupportMapFragment supportMapFragment = (SupportMapFragment) fragment;
        supportMapFragment.getMapAsync(new com.google.android.gms.maps.OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                onMapReadyCallback.onMapReady();
            }
        });
    }

    @Override
    public void uiSetting() {

        mGoogleMap.getUiSettings().setCompassEnabled(false);  //隐藏指南针
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);  //隐藏地图系统默认的放缩按钮
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);  //禁止通过手势旋转
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(false);  //禁止通过手势倾斜
        mGoogleMap.getUiSettings().setIndoorLevelPickerEnabled(false);  //隐藏层级选取器
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);  //隐藏地图工具栏
    }

    @Override
    public void setLoadOfflineData(boolean enabled) {

    }

    @Override
    public void zoomIn(int duration) {

        mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn(), duration, null);
    }

    @Override
    public void zoomOut(int duration) {

        mGoogleMap.animateCamera(CameraUpdateFactory.zoomOut(), duration, null);
    }

    @Override
    public void zoomBy(float level, int duration) {

        mGoogleMap.animateCamera(CameraUpdateFactory.zoomBy(level), duration, null);
    }

    @Override
    public void animateCamera(MXLatLng mxLatLng, float level, int duration) {

        LatLng latLng = new LatLng(mxLatLng.latitudeWGS84, mxLatLng.longitudeWGS84);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, level), duration, null);
    }

    @Override
    public void animateCamera(List<MXLatLng> mxLatLngList, int duration) {

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (MXLatLng mxLatLng : mxLatLngList) {
            boundsBuilder.include(new LatLng(mxLatLng.latitudeWGS84, mxLatLng.longitudeWGS84));
        }
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 50), duration, null);
    }

    @Override
    public String addMarker(MXMarker mxMarker) {

        LatLng latLng = new LatLng(mxMarker.mxLatLng.latitudeWGS84, mxMarker.mxLatLng.longitudeWGS84);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).icon(BitmapDescriptorFactory.fromBitmap(mxMarker.bitmap))
                .anchor((float) 0.5, (float) 0.65).zIndex(0).flat(true);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        return marker.getId();
    }

    @Override
    public String addMarker(MXMarker mxMarker, boolean isAnimate) {

        return null;
    }

    @Override
    public List<String> addMarkers(List<MXMarker> mxMarkerList) {

        List<String> markerIdList = new ArrayList<>();
        for (MXMarker mxMarker : mxMarkerList) {
            markerIdList.add(addMarker(mxMarker));
        }
        return markerIdList;
    }

    @Override
    public void updateMarker(MXMarker mxMarker) {

    }

    @Override
    public void removeMarker(MXMarker mxMarker) {

    }

    @Override
    public void addPolyline(MXPolyline mxPolyline) {

        List<LatLng> latLngList = new ArrayList<>();
        for (MXLatLng mxLatLng : mxPolyline.mxLatLngList) {
            latLngList.add(new LatLng(mxLatLng.latitudeWGS84, mxLatLng.longitudeWGS84));
        }

        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(latLngList)
                .color(mxPolyline.colorId)
                .width(mxPolyline.width);
        Polyline polyline = mGoogleMap.addPolyline(polylineOptions);
        mxPolyline.mapPolyline = polyline;
    }

    @Override
    public void removePolyline(MXPolyline mxPolyline) {

        if(mxPolyline!=null&&mxPolyline.mapPolyline!=null){
            Polyline polyline = (Polyline)mxPolyline.mapPolyline;
            polyline.remove();
            mxPolyline.mapPolyline=null;
        }
    }

    @Override
    public void clear() {

        mGoogleMap.clear();
    }

    @Override
    public void setMapType(MapConstant.MapType mapType) {

        switch (mapType) {
            case MAP_TYPE_NORMAL:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case MAP_TYPE_SATELLITE:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            default:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
        }
    }

    @Override
    public void setOnMarkerClickListener(OnMarkerClickListener markerClickListener) {

        mGoogleMap.setOnMarkerClickListener(marker -> markerClickListener.onMarkerClick(marker.getId()));
    }

    @Override
    public void setOnCameraChangeListener(OnCameraChangeListener onCameraChangeListener) {

        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                CameraPosition cameraPosition = mGoogleMap.getCameraPosition();
                onCameraChangeListener.onCameraChange(cameraPosition.zoom, cameraPosition.target.latitude, cameraPosition.target.longitude);
            }
        });

        mGoogleMap.setOnCameraMoveCanceledListener(new GoogleMap.OnCameraMoveCanceledListener() {
            @Override
            public void onCameraMoveCanceled() {
                CameraPosition cameraPosition = mGoogleMap.getCameraPosition();
                onCameraChangeListener.onCameraChange(cameraPosition.zoom, cameraPosition.target.latitude, cameraPosition.target.longitude);
            }
        });
    }
}
