package com.ishare.mapadapter.amapimpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
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
import com.amap.api.maps.model.IndoorBuildingInfo;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.ishare.mapadapter.MapConstant;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.bean.MXLatLng;
import com.ishare.mapadapter.bean.MXMapScale;
import com.ishare.mapadapter.bean.MXMarker;
import com.ishare.mapadapter.bean.MXPolyline;
import com.ishare.mapadapter.indoor.MapIndoorInfo;

import java.util.ArrayList;
import java.util.List;

public class AMapImpl implements MXMap {

    private static final String TAG = "AMapImpl";

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
    public void uiSetting(Context context) {

        mAMap.getUiSettings().setZoomControlsEnabled(false);  //隐藏地图系统默认的放缩按钮
        mAMap.getUiSettings().setRotateGesturesEnabled(false);  //禁止通过手势旋转
        mAMap.getUiSettings().setTiltGesturesEnabled(false);  //禁止通过手势倾斜
        mAMap.showIndoorMap(true);
        mAMap.getUiSettings().setIndoorSwitchEnabled(false);  // 关闭SDK自带的室内地图控件
        mAMap.getUiSettings().setScaleControlsEnabled(false);
    }

    @Override
    public void showMyLocation(Context context, boolean enable, Bitmap icon){

        // 实现定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.anchor((float) 0.5, (float) 0.5);
        myLocationStyle.strokeWidth(0f);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        myLocationStyle.strokeColor(Color.TRANSPARENT);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(icon));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.showMyLocation(enable);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mAMap.setMyLocationEnabled(enable);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
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
        for(MXLatLng mxLatLng : mxLatLngList){
            boundsBuilder.include(new LatLng(mxLatLng.latitudeGCJ02, mxLatLng.longitudeGCJ02));
        }
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 50), duration, null);
    }

    @Override
    public String addMarker(Context context, MXMarker mxMarker) {

        BitmapDescriptor bitmapDescriptor = AMapUtilsImpl.getMapBitmapDescriptor(context, mxMarker.title, mxMarker.bitmap);

        LatLng latLng = new LatLng(mxMarker.mxLatLng.latitudeGCJ02, mxMarker.mxLatLng.longitudeGCJ02);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng)
                .icon(bitmapDescriptor)
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
    public String addMarker(Context context, MXMarker mxMarker, boolean isAnimate) {

        if (!isAnimate) {
            return addMarker(context, mxMarker);
        }

        ArrayList<BitmapDescriptor> bitmapDescriptors = new ArrayList<>();
        for (Bitmap bitmap : mxMarker.bitmapArray) {
            bitmapDescriptors.add(AMapUtilsImpl.getMapBitmapDescriptor(context, mxMarker.title, bitmap));
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
    public List<String> addMarkers(Context context, List<MXMarker> mxMarkerList) {

        List<String> markerIdList = new ArrayList<>();
        for(MXMarker mxMarker : mxMarkerList){
            markerIdList.add(addMarker(context, mxMarker));
        }
        return markerIdList;
    }

    @Override
    public void updateMarker(Context context, MXMarker mxMarker) {

        if (mxMarker != null && mxMarker.mapMarker != null) {

            BitmapDescriptor bitmapDescriptor = AMapUtilsImpl.getMapBitmapDescriptor(context, mxMarker.title, mxMarker.bitmap);
            ArrayList<BitmapDescriptor> bitmapDescriptors = new ArrayList<>();
            bitmapDescriptors.add(bitmapDescriptor);

            Marker marker = (Marker) mxMarker.mapMarker;
            marker.setPosition(new LatLng(mxMarker.mxLatLng.latitudeGCJ02, mxMarker.mxLatLng.longitudeGCJ02));
            marker.setAnchor(mxMarker.anchorU, mxMarker.anchorV);
            marker.setZIndex(mxMarker.zIndex);
            marker.setIcon(bitmapDescriptor);
            marker.setIcons(bitmapDescriptors);
            marker.setRotateAngle(mxMarker.rotateAngle);
        }
    }

    @Override
    public void updateMarker(Context context, MXMarker mxMarker, boolean isAnimate) {

        if (!isAnimate) {
            updateMarker(context, mxMarker);
            return;
        }

        if (mxMarker != null && mxMarker.mapMarker != null) {

            ArrayList<BitmapDescriptor> bitmapDescriptors = new ArrayList<>();
            for (Bitmap bitmap : mxMarker.bitmapArray) {
                bitmapDescriptors.add(AMapUtilsImpl.getMapBitmapDescriptor(context, mxMarker.title, bitmap));
            }

            Marker marker = (Marker) mxMarker.mapMarker;
            marker.setPosition(new LatLng(mxMarker.mxLatLng.latitudeGCJ02, mxMarker.mxLatLng.longitudeGCJ02));
            marker.setAnchor(mxMarker.anchorU, mxMarker.anchorV);
            marker.setZIndex(mxMarker.zIndex);
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(mxMarker.bitmap));
            marker.setIcons(bitmapDescriptors);
            marker.setRotateAngle(mxMarker.rotateAngle);
        }
    }

    @Override
    public void removeMarker(MXMarker mxMarker) {

        if (mxMarker != null && mxMarker.mapMarker != null) {
            Marker marker = (Marker) mxMarker.mapMarker;
            marker.remove();
            mxMarker.mapMarker = null;
            mxMarker = null;
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
            Polyline polyline = (Polyline) mxPolyline.mapPolyline;
            polyline.remove();
            mxPolyline.mapPolyline = null;
            mxPolyline = null;
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
    public MXMapScale getMapScaleParams(float zoom) {

        CameraPosition cameraPosition = mAMap.getCameraPosition();
        float perPixel = mAMap.getScalePerPixel();
        if (cameraPosition == null) {
            return null;
        }

        MXMapScale mapScale = new MXMapScale();
        if (cameraPosition.zoom >= 18.8) {
            mapScale.unit = 1;
            mapScale.text = 10;
            mapScale.width = (int) (10 / perPixel);
        } else if (cameraPosition.zoom >= 18) {
            mapScale.unit = 1;
            mapScale.text = 25;
            mapScale.width = (int) (25 / perPixel);
        } else if (cameraPosition.zoom > 17.1) {
            mapScale.unit = 1;
            mapScale.text = 50;
            mapScale.width = (int) (50 / perPixel);
        } else if (cameraPosition.zoom > 16.1) {
            mapScale.unit = 1;
            mapScale.text = 100;
            mapScale.width = (int) (100 / perPixel);
        } else if (cameraPosition.zoom > 15.1) {
            mapScale.unit = 1;
            mapScale.text = 200;
            mapScale.width = (int) (200 / perPixel);
        } else if (cameraPosition.zoom > 14.1) {
            mapScale.unit = 1;
            mapScale.text = 500;
            mapScale.width = (int) (500 / perPixel);
        } else if (cameraPosition.zoom > 13.1) {
            mapScale.unit = 2;
            mapScale.text = 1;
            mapScale.width = (int) (1000 / perPixel);
        } else if (cameraPosition.zoom > 12.1) {
            mapScale.unit = 2;
            mapScale.text = 2;
            mapScale.width = (int) (2000 / perPixel);
        } else if (cameraPosition.zoom > 11.1) {
            mapScale.unit = 2;
            mapScale.text = 5;
            mapScale.width = (int) (5000 / perPixel);
        } else if (cameraPosition.zoom > 10.1) {
            mapScale.unit = 2;
            mapScale.text = 10;
            mapScale.width = (int) (10 * 1000 / perPixel);
        } else if (cameraPosition.zoom > 9.1) {
            mapScale.unit = 2;
            mapScale.text = 20;
            mapScale.width = (int) (20 * 1000 / perPixel);
        } else if (cameraPosition.zoom > 8.1) {
            mapScale.unit = 2;
            mapScale.text = 30;
            mapScale.width = (int) (30 * 1000 / perPixel);
        } else if (cameraPosition.zoom > 7.1) {
            mapScale.unit = 2;
            mapScale.text = 50;
            mapScale.width = (int) (50 * 1000 / perPixel);
        } else if (cameraPosition.zoom > 6.1) {
            mapScale.unit = 2;
            mapScale.text = 100;
            mapScale.width = (int) (100 * 1000 / perPixel);
        } else if (cameraPosition.zoom > 5.1) {
            mapScale.unit = 2;
            mapScale.text = 200;
            mapScale.width = (int) (200 * 1000 / perPixel);
        } else if (cameraPosition.zoom > 4.1) {
            mapScale.unit = 2;
            mapScale.text = 500;
            mapScale.width = (int) (500 * 1000 / perPixel);
        } else if (cameraPosition.zoom > 3.1) {
            mapScale.unit = 2;
            mapScale.text = 1000;
            mapScale.width = (int) (1000 * 1000 / perPixel);
        } else if (cameraPosition.zoom > 1.0) {
            mapScale.unit = 2;
            mapScale.text = 1500;
            mapScale.width = (int) (1500 * 1000 / perPixel);
        }
        return mapScale;
    }

    // 高德sdk中实际用到的是IndoorBuildingInfo的子类
    private IndoorBuildingInfo mIndoorBuildingInfo;
    @Override
    public void setIndoorMapFloor(MapIndoorInfo mapIndoorInfo) {

        Log.i(TAG, " mapIndoorInfo = " + mapIndoorInfo.toString());
        //IndoorBuildingInfo indoorBuildingInfo = new IndoorBuildingInfo();
        mIndoorBuildingInfo.activeFloorName = mapIndoorInfo.activeFloorName;
        mIndoorBuildingInfo.activeFloorIndex = mapIndoorInfo.activeFloorIndex;
        mIndoorBuildingInfo.poiid = mapIndoorInfo.poiid;
        mIndoorBuildingInfo.floor_indexs = mapIndoorInfo.floor_indexs;
        mIndoorBuildingInfo.floor_names = mapIndoorInfo.floor_names;
        mAMap.setIndoorBuildingInfo(mIndoorBuildingInfo);
    }

    @Override
    public void setOnMapClickListener(final MXMap.OnMapClickListener mapClickListener) {

        mAMap.setOnMapClickListener(latLng -> mapClickListener.onMapClick());
    }

    @Override
    public void setOnMarkerClickListener(final OnMarkerClickListener markerClickListener) {

        mAMap.setOnMarkerClickListener(marker -> markerClickListener.onMarkerClick(marker.getId()));
    }

    @Override
    public void setOnCameraChangeListener(final OnCameraChangeListener onCameraChangeListener) {

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

    @Override
    public void setOnIndoorMapListener(final OnIndoorMapListener onIndoorMapListener) {

        mAMap.setOnIndoorBuildingActiveListener(indoorBuildingInfo -> {

            mIndoorBuildingInfo = indoorBuildingInfo;
            if (indoorBuildingInfo != null) {
                MapIndoorInfo mapIndoorInfo = new MapIndoorInfo();
                mapIndoorInfo.activeFloorName = indoorBuildingInfo.activeFloorName;
                mapIndoorInfo.activeFloorIndex = indoorBuildingInfo.activeFloorIndex;
                mapIndoorInfo.poiid = indoorBuildingInfo.poiid;
                mapIndoorInfo.floor_indexs = indoorBuildingInfo.floor_indexs;
                mapIndoorInfo.floor_names = indoorBuildingInfo.floor_names;
                onIndoorMapListener.onIndoormapInfo(true, mapIndoorInfo);
            } else {
                onIndoorMapListener.onIndoormapInfo(false, null);
            }
        });
    }
}
