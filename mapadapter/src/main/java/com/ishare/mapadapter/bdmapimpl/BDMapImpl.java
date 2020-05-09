package com.ishare.mapadapter.bdmapimpl;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapFragment;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.ishare.mapadapter.MapConstant;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.bean.MXLatLng;
import com.ishare.mapadapter.bean.MXMarker;
import com.ishare.mapadapter.bean.MXPolyline;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class BDMapImpl implements MXMap {

    private MapView mapView;
    private BaiduMap mBDMap;

    /**
     * Application onCreate调用
     * @param context 传入ApplicationContext
     */
    public static void initAppMapManager(Context context) {
        SDKInitializer.initialize(context);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    /**
     * Application onCreate调用
     * @param context 传入ApplicationContext
     * @param sdcardDir sdcard路径
     */
    public static void initAppMapManager(Context context,String sdcardDir) {
        SDKInitializer.initialize(sdcardDir, context);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    /**
     * Activity onCreate调用
     * @param context 传入ApplicationContext
     */
    public static void initActivityMapManager(Context context,String sdcardDir) {

    }

    @Override
    public void createMap(View view, MXMap.OnMapReadyCallback onMapReadyCallback) {

        mapView = (MapView) view;
        mBDMap = mapView.getMap();
        onMapReadyCallback.onMapReady();
    }

    @Override
    public void createMap(Fragment fragment, MXMap.OnMapReadyCallback onMapReadyCallback) {

        MapFragment mapFragment = (MapFragment) fragment;
        mBDMap = mapFragment.getBaiduMap();
        onMapReadyCallback.onMapReady();
    }

    @Override
    public void createMap(android.support.v4.app.Fragment fragment, MXMap.OnMapReadyCallback onMapReadyCallback) {

        SupportMapFragment supportMapFragment = (SupportMapFragment) fragment;
        // MapView创建较慢，延迟回调
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mapView = supportMapFragment.getMapView();
                mBDMap = supportMapFragment.getBaiduMap();
                onMapReadyCallback.onMapReady();
            }
        }, 200);
    }

    @Override
    public void uiSetting() {

        if (mapView != null) {
            mapView.showScaleControl(false);
            mapView.showZoomControls(false);
        }
        mBDMap.getUiSettings().setCompassEnabled(false);
        mBDMap.getUiSettings().setOverlookingGesturesEnabled(false);
        mBDMap.getUiSettings().setRotateGesturesEnabled(false);
    }

    @Override
    public void setLoadOfflineData(boolean enabled) {

    }

    @Override
    public void zoomIn(int duration) {

        mBDMap.animateMapStatus(MapStatusUpdateFactory.zoomIn(), duration);
    }

    @Override
    public void zoomOut(int duration) {

        mBDMap.animateMapStatus(MapStatusUpdateFactory.zoomOut(), duration);
    }

    @Override
    public void zoomBy(float level, int duration) {

        mBDMap.animateMapStatus(MapStatusUpdateFactory.zoomBy(level), duration);
    }

    @Override
    public void animateCamera(MXLatLng mxLatLng, float level, int duration) {

        LatLng latLng = new LatLng(mxLatLng.latitudeBD09, mxLatLng.longitudeBD09);
        mBDMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(latLng, level), duration);
    }

    @Override
    public void animateCamera(List<MXLatLng> mxLatLngList, int duration) {

        LatLngBounds.Builder boundsBuilder=new LatLngBounds.Builder();
        for(MXLatLng mxLatLng:mxLatLngList){
            boundsBuilder.include(new LatLng(mxLatLng.latitudeBD09,mxLatLng.longitudeBD09));
        }
        mBDMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(boundsBuilder.build()), duration);
    }

    @Override
    public String addMarker(MXMarker mxMarker) {

        LatLng latLng = new LatLng(mxMarker.mxLatLng.latitudeBD09, mxMarker.mxLatLng.longitudeBD09);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(mxMarker.bitmap))
                .anchor(mxMarker.anchorU, mxMarker.anchorV)
                .zIndex((int)mxMarker.zIndex)
                .flat(mxMarker.isFlat);
        Marker marker = (Marker) mBDMap.addOverlay(markerOptions);
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

        LatLng latLng = new LatLng(mxMarker.mxLatLng.latitudeBD09, mxMarker.mxLatLng.longitudeBD09);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng)
                .icons(bitmapDescriptors)
                .anchor(mxMarker.anchorU, mxMarker.anchorV)
                .zIndex((int)mxMarker.zIndex)
                .flat(mxMarker.isFlat);
        Marker marker = (Marker) mBDMap.addOverlay(markerOptions);
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
            marker.setZIndex((int)mxMarker.zIndex);
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(mxMarker.bitmap));
            marker.setRotate(mxMarker.rotateAngle);
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
            latLngList.add(new LatLng(mxLatLng.latitudeBD09, mxLatLng.longitudeBD09));
        }

        OverlayOptions polylineOptions = new PolylineOptions()
                .width((int)mxPolyline.width)
                .points(latLngList)
                .color(mxPolyline.colorId);
        Polyline polyline = (Polyline) mBDMap.addOverlay(polylineOptions);
        mxPolyline.mapPolyline=polyline;
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
        mBDMap.clear();
    }

    @Override
    public void setMapType(MapConstant.MapType mapType) {

        switch (mapType) {
            case MAP_TYPE_NORMAL:
                mBDMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case MAP_TYPE_SATELLITE:
                mBDMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            default:
                mBDMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
        }
    }

    @Override
    public void setOnMarkerClickListener(OnMarkerClickListener markerClickListener) {

        mBDMap.setOnMarkerClickListener(marker -> markerClickListener.onMarkerClick(marker.getId()));
    }

    @Override
    public void setOnCameraChangeListener(OnCameraChangeListener onCameraChangeListener) {

        mBDMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                onCameraChangeListener.onCameraChange(mapStatus.zoom, mapStatus.target.latitude, mapStatus.target.longitude);
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
                onCameraChangeListener.onCameraChange(mapStatus.zoom, mapStatus.target.latitude, mapStatus.target.longitude);
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                onCameraChangeListener.onCameraChange(mapStatus.zoom, mapStatus.target.latitude, mapStatus.target.longitude);
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                onCameraChangeListener.onCameraChange(mapStatus.zoom, mapStatus.target.latitude, mapStatus.target.longitude);
            }
        });
    }
}
