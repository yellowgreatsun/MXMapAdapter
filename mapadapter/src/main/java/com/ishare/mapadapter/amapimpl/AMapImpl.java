package com.ishare.mapadapter.amapimpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapFragment;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.ishare.mapadapter.MapConstant;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.bean.MXLatLng;
import com.ishare.mapadapter.bean.MXMarker;
import com.ishare.mapadapter.bean.MXPolyline;

import java.util.ArrayList;
import java.util.List;

public class AMapImpl implements MXMap {

    private AMap mAMap;

    /**
     * Application onCreate调用
     *
     * @param context 传入ApplicationContext
     */
    public static void initAppMapManager(Context context) {
    }

    /**
     * Activity onCreate调用
     *
     * @param context   传入context
     * @param sdcardDir sdcard路径
     */
    public static void initActivityMapManager(Context context, String sdcardDir) {

        try {
            MapsInitializer.initialize(context);
            MapsInitializer.sdcardDir = sdcardDir;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createMap(View view, MXMap.OnMapReadyCallback onMapReadyCallback) {

        MapView mapView = (MapView) view;
        mAMap = mapView.getMap();
        onMapReadyCallback.onMapReady();
    }

    @Override
    public void createMap(android.app.Fragment fragment, MXMap.OnMapReadyCallback onMapReadyCallback) {

        MapFragment mapFragment = (MapFragment) fragment;
        mAMap = mapFragment.getMap();
        onMapReadyCallback.onMapReady();
    }

    @Override
    public void createMap(android.support.v4.app.Fragment fragment, MXMap.OnMapReadyCallback onMapReadyCallback) {

        SupportMapFragment supportMapFragment = (SupportMapFragment) fragment;
        mAMap = supportMapFragment.getMap();
        onMapReadyCallback.onMapReady();
    }

    @Override
    public void uiSetting() {

        mAMap.getUiSettings().setZoomControlsEnabled(false);  //隐藏地图系统默认的放缩按钮
        mAMap.getUiSettings().setRotateGesturesEnabled(false);  //禁止通过手势旋转
        mAMap.getUiSettings().setTiltGesturesEnabled(false);  //禁止通过手势倾斜
        mAMap.showIndoorMap(true);
        mAMap.getUiSettings().setIndoorSwitchEnabled(false);  // 关闭SDK自带的室内地图控件
    }

    @Override
    public void setLoadOfflineData(boolean enabled) {

        mAMap.setLoadOfflineData(enabled);
    }

    @Override
    public void zoomIn(int duration) {

        mAMap.animateCamera(CameraUpdateFactory.zoomIn(), duration, null);
    }

    @Override
    public void zoomOut(int duration) {

        mAMap.animateCamera(CameraUpdateFactory.zoomOut(), duration, null);
    }

    @Override
    public void zoomBy(float level, int duration) {

        mAMap.animateCamera(CameraUpdateFactory.zoomBy(level), duration, null);
    }

    @Override
    public void animateCamera(MXLatLng mxLatLng, float level, int duration) {

        LatLng latLng = new LatLng(mxLatLng.latitudeGCJ02, mxLatLng.longitudeGCJ02);
        mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, level), duration, null);
    }

    @Override
    public void animateCamera(List<MXLatLng> mxLatLngList, int duration) {

        LatLngBounds.Builder boundsBuilder=new LatLngBounds.Builder();
        for(MXLatLng mxLatLng:mxLatLngList){
            boundsBuilder.include(new LatLng(mxLatLng.latitudeGCJ02,mxLatLng.longitudeGCJ02));
        }
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 50), duration, null);
    }

    @Override
    public String addMarker(MXMarker mxMarker) {

        LatLng latLng = new LatLng(mxMarker.mxLatLng.latitudeGCJ02, mxMarker.mxLatLng.longitudeGCJ02);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(mxMarker.bitmap))
                .anchor(mxMarker.anchorU, mxMarker.anchorV)
                .zIndex(mxMarker.zIndex)
                .rotateAngle(mxMarker.rotateAngle)
                .setFlat(mxMarker.isFlat);
        Marker marker = mAMap.addMarker(markerOptions);
        mxMarker.mapMarker = marker;
        mxMarker.mapMarkerId = marker.getId();
        return marker.getId();
    }

    @Override
    public String addMarker(MXMarker mxMarker, boolean isAnimate) {

        if (!isAnimate) {
            return addMarker(mxMarker);
        }

        ArrayList<BitmapDescriptor> bitmapDescriptors = new ArrayList<>();
        for (Bitmap bitmap : mxMarker.bitmapArray) {
            bitmapDescriptors.add(BitmapDescriptorFactory.fromBitmap(bitmap));
        }

        LatLng latLng = new LatLng(mxMarker.mxLatLng.latitudeGCJ02, mxMarker.mxLatLng.longitudeGCJ02);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).icons(bitmapDescriptors)
                .anchor(mxMarker.anchorU, mxMarker.anchorV).zIndex(mxMarker.zIndex)
                .rotateAngle(mxMarker.rotateAngle).setFlat(mxMarker.isFlat);
        Marker marker = mAMap.addMarker(markerOptions);
        mxMarker.mapMarker = marker;
        mxMarker.mapMarkerId = marker.getId();
        return marker.getId();
    }

    @Override
    public List<String> addMarkers(List<MXMarker> mxMarkerList) {

        List<String> markerIdList = new ArrayList<>();
        for(MXMarker mxMarker:mxMarkerList){
            markerIdList.add(addMarker(mxMarker));
        }
        return markerIdList;
    }

    @Override
    public void updateMarker(MXMarker mxMarker) {

        if (mxMarker != null && mxMarker.mapMarker != null) {
            Marker marker = (Marker) mxMarker.mapMarker;
            marker.setPosition(new LatLng(mxMarker.mxLatLng.latitudeGCJ02, mxMarker.mxLatLng.longitudeGCJ02));
            marker.setAnchor(mxMarker.anchorU,mxMarker.anchorV);
            marker.setZIndex(mxMarker.zIndex);
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(mxMarker.bitmap));
            marker.setRotateAngle(mxMarker.rotateAngle);
        }
    }

    @Override
    public void removeMarker(MXMarker mxMarker) {

        if (mxMarker != null && mxMarker.mapMarker != null) {
            Marker marker = (Marker) mxMarker.mapMarker;
            marker.remove();
            mxMarker.mapMarker = null;
        }
    }

    @Override
    public void addPolyline(MXPolyline mxPolyline) {

        List<LatLng> latLngList = new ArrayList<>();
        for (MXLatLng mxLatLng : mxPolyline.mxLatLngList) {
            latLngList.add(new LatLng(mxLatLng.latitudeGCJ02, mxLatLng.longitudeGCJ02));
        }
        Polyline polyline = mAMap.addPolyline((new PolylineOptions()).addAll(latLngList).color(mxPolyline.colorId).width(mxPolyline.width));
        mxPolyline.mapPolyline = polyline;
    }

    @Override
    public void removePolyline(MXPolyline mxPolyline) {

        if (mxPolyline != null && mxPolyline.mapPolyline != null) {
            Marker marker = (Marker) mxPolyline.mapPolyline;
            marker.remove();
            mxPolyline.mapPolyline = null;
        }
    }

    @Override
    public void clear() {
        mAMap.clear();
    }

    @Override
    public void setMapType(MapConstant.MapType mapType) {

        switch (mapType) {
            case MAP_TYPE_NORMAL:
                mAMap.setMapType(AMap.MAP_TYPE_NORMAL);
                break;
            case MAP_TYPE_SATELLITE:
                mAMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                break;
            default:
                mAMap.setMapType(AMap.MAP_TYPE_NORMAL);
                break;
        }
    }

    @Override
    public void setOnMarkerClickListener(OnMarkerClickListener markerClickListener) {

        mAMap.setOnMarkerClickListener(marker -> markerClickListener.onMarkerClick(marker.getId()));
    }

    @Override
    public void setOnCameraChangeListener(OnCameraChangeListener onCameraChangeListener) {

        mAMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                onCameraChangeListener.onCameraChange(cameraPosition.zoom, cameraPosition.target.latitude, cameraPosition.target.longitude);
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                onCameraChangeListener.onCameraChangeFinish(cameraPosition.zoom, cameraPosition.target.latitude, cameraPosition.target.longitude);
            }
        });
    }
}
