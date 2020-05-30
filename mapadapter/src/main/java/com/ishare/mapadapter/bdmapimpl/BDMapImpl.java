package com.ishare.mapadapter.bdmapimpl;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
import com.baidu.mapapi.map.MapFragment;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.ishare.mapadapter.MapConstant;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.bean.MXLatLng;
import com.ishare.mapadapter.bean.MXMapScale;
import com.ishare.mapadapter.bean.MXMarker;
import com.ishare.mapadapter.bean.MXPolyline;
import com.ishare.mapadapter.indoor.MapIndoorInfo;

import java.util.ArrayList;
import java.util.List;

public class BDMapImpl implements MXMap {

    private static final String TAG = "BDMapImpl";

    private MapView mapView;
    private BaiduMap mBDMap;
    private Context context;

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
    public void createMap(android.support.v4.app.Fragment fragment, final MXMap.OnMapReadyCallback onMapReadyCallback) {

        final SupportMapFragment supportMapFragment = (SupportMapFragment) fragment;
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
    public void uiSetting(Context context) {

        if (mapView != null) {
            mapView.showScaleControl(false);
            mapView.showZoomControls(false);
        }
        mBDMap.getUiSettings().setCompassEnabled(false);
        mBDMap.getUiSettings().setOverlookingGesturesEnabled(false);
        mBDMap.getUiSettings().setRotateGesturesEnabled(false);
        mBDMap.setIndoorEnable(true);
    }

    @Override
    public void showMyLocation(Context context, boolean enable, Bitmap icon) {

        MyLocationConfiguration mLocationConfiguration
                = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true,
                BitmapDescriptorFactory.fromBitmap(icon), Color.TRANSPARENT, Color.TRANSPARENT);
        mBDMap.setMyLocationConfiguration(mLocationConfiguration);
        mBDMap.setMyLocationEnabled(enable);

        //定位初始化
        LocationClient mLocationClient = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new BDAbstractLocationListener(){
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation == null) {
                    return;
                }
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(bdLocation.getRadius())
                        .direction(bdLocation.getDirection())  // 此处设置开发者获取到的方向信息，顺时针0-360
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .build();
                mBDMap.setMyLocationData(locData);
            }
        });
        if (enable)
            mLocationClient.start();
        else
            mLocationClient.stop();
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
        for(MXLatLng mxLatLng : mxLatLngList){
            boundsBuilder.include(new LatLng(mxLatLng.latitudeBD09, mxLatLng.longitudeBD09));
        }
        mBDMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(boundsBuilder.build()), duration);
    }

    @Override
    public String addMarker(Context context, MXMarker mxMarker) {

        BitmapDescriptor bitmapDescriptor = BDMapUtilsImpl.getMapBitmapDescriptor(context, mxMarker.title, mxMarker.bitmap);

        LatLng latLng = new LatLng(mxMarker.mxLatLng.latitudeBD09, mxMarker.mxLatLng.longitudeBD09);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng)
                .icon(bitmapDescriptor)
                .anchor(mxMarker.anchorU, mxMarker.anchorV)
                .zIndex((int) mxMarker.zIndex)
                .flat(mxMarker.isFlat);
        Marker marker = (Marker) mBDMap.addOverlay(markerOptions);
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
            bitmapDescriptors.add(BDMapUtilsImpl.getMapBitmapDescriptor(context, mxMarker.title, bitmap));
        }

        LatLng latLng = new LatLng(mxMarker.mxLatLng.latitudeBD09, mxMarker.mxLatLng.longitudeBD09);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng)
                .icons(bitmapDescriptors)
                .anchor(mxMarker.anchorU, mxMarker.anchorV)
                .zIndex((int) mxMarker.zIndex)
                .flat(mxMarker.isFlat);
        Marker marker = (Marker) mBDMap.addOverlay(markerOptions);
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
    public void updateMarker(Context context, MXMarker mxMarker, boolean isAnimate) {

        if (!isAnimate) {
            updateMarker(context, mxMarker);
            return;
        }

        if (mxMarker != null && mxMarker.mapMarker != null) {

            BitmapDescriptor bitmapDescriptor = BDMapUtilsImpl.getMapBitmapDescriptor(context, mxMarker.title, mxMarker.bitmap);

            ArrayList<BitmapDescriptor> bitmapDescriptors = new ArrayList<>();
            for (Bitmap bitmap : mxMarker.bitmapArray) {
                bitmapDescriptors.add(BDMapUtilsImpl.getMapBitmapDescriptor(context, mxMarker.title, bitmap));
            }

            Marker marker = (Marker) mxMarker.mapMarker;
            marker.setPosition(new LatLng(mxMarker.mxLatLng.latitudeBD09, mxMarker.mxLatLng.longitudeBD09));
            marker.setAnchor(mxMarker.anchorU, mxMarker.anchorV);
            marker.setZIndex((int) mxMarker.zIndex);
            marker.setIcon(bitmapDescriptor);
            marker.setIcons(bitmapDescriptors);
            marker.setRotate(mxMarker.rotateAngle);
            marker.setIcons(new ArrayList<BitmapDescriptor>());
        }
    }

    @Override
    public void updateMarker(Context context, MXMarker mxMarker) {

        if (mxMarker != null && mxMarker.mapMarker != null) {

            BitmapDescriptor bitmapDescriptor = BDMapUtilsImpl.getMapBitmapDescriptor(context, mxMarker.title, mxMarker.bitmap);
            ArrayList<BitmapDescriptor> bitmapDescriptors = new ArrayList<>();
            bitmapDescriptors.add(bitmapDescriptor);

            Marker marker = (Marker) mxMarker.mapMarker;
            marker.setPosition(new LatLng(mxMarker.mxLatLng.latitudeBD09, mxMarker.mxLatLng.longitudeBD09));
            marker.setAnchor(mxMarker.anchorU, mxMarker.anchorV);
            marker.setZIndex((int) mxMarker.zIndex);
            marker.setIcon(bitmapDescriptor);
            marker.setIcons(bitmapDescriptors);
            marker.setRotate(mxMarker.rotateAngle);
            marker.setIcons(new ArrayList<BitmapDescriptor>());
        }
    }

    @Override
    public void removeMarker(MXMarker mxMarker) {

        if (mxMarker != null && mxMarker.mapMarker != null) {
            Marker marker = (Marker) mxMarker.mapMarker;
            Log.i(TAG, "hideInfoWindow size= " + mBDMap.getAllInfoWindows().size());
            mBDMap.hideInfoWindow();
            marker.remove();
            mxMarker.mapMarker = null;
            mxMarker = null;
        }
    }

    @Override
    public void addPolyline(MXPolyline mxPolyline) {

        List<LatLng> latLngList = new ArrayList<>();
        for (MXLatLng mxLatLng : mxPolyline.mxLatLngList) {
            latLngList.add(new LatLng(mxLatLng.latitudeBD09, mxLatLng.longitudeBD09));
        }

        OverlayOptions polylineOptions = new PolylineOptions()
                .width((int) mxPolyline.width / 2)
                .points(latLngList)
                .color(mxPolyline.colorId);
        Polyline polyline = (Polyline) mBDMap.addOverlay(polylineOptions);
        mxPolyline.mapPolyline=polyline;
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
        mBDMap.clear();
        mBDMap.hideInfoWindow();
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
    public MXMapScale getMapScaleParams(float zoom) {

        MapStatus mapStatus = mBDMap.getMapStatus();
        if (mapStatus == null)
            return null;
        if(mBDMap.getProjection()==null)
            return null;

        MXMapScale mapScale = new MXMapScale();
        if (mapStatus.zoom >= 21.0) {
            mapScale.unit = 1;
            mapScale.text = 5;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(5.0f));
        } else if (mapStatus.zoom < 21.0 && mapStatus.zoom >= 20.0) {
            mapScale.unit = 1;
            mapScale.text = 10;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(10.0f));
        } else if (mapStatus.zoom < 20.0 && mapStatus.zoom >= 19.0) {
            mapScale.unit = 1;
            mapScale.text = 20;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(20.0f));
        } else if (mapStatus.zoom < 19.0 && mapStatus.zoom >= 18.0) {
            mapScale.unit = 1;
            mapScale.text = 50;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(50.0f));
        } else if (mapStatus.zoom < 18.0 && mapStatus.zoom >= 17.0) {
            mapScale.unit = 1;
            mapScale.text = 100;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(100.0f));
        } else if (mapStatus.zoom < 17.0 && mapStatus.zoom >= 16.0) {
            mapScale.unit = 1;
            mapScale.text = 200;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(200.0f));
        } else if (mapStatus.zoom < 16.0 && mapStatus.zoom >= 15.0) {
            mapScale.unit = 1;
            mapScale.text = 500;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(500.0f));
        } else if (mapStatus.zoom < 15.0 && mapStatus.zoom >= 14.0) {
            mapScale.unit = 2;
            mapScale.text = 1;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(1000.0f));
        } else if (mapStatus.zoom < 14.0 && mapStatus.zoom >= 13.0) {
            mapScale.unit = 2;
            mapScale.text = 2;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(2 * 1000.0f));
        } else if (mapStatus.zoom < 13.0 && mapStatus.zoom >= 12.0) {
            mapScale.unit = 2;
            mapScale.text = 5;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(5 * 1000.0f));
        } else if (mapStatus.zoom < 12.0 && mapStatus.zoom >= 11.0) {
            mapScale.unit = 2;
            mapScale.text = 10;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(10 * 1000.0f));
        } else if (mapStatus.zoom < 11.0 && mapStatus.zoom >= 10.0) {
            mapScale.unit = 2;
            mapScale.text = 20;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(20 * 1000.0f));
        } else if (mapStatus.zoom < 10.0 && mapStatus.zoom >= 9.0) {
            mapScale.unit = 2;
            mapScale.text = 25;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(25 * 1000.0f));
        } else if (mapStatus.zoom < 9.0 && mapStatus.zoom >= 8.0) {
            mapScale.unit = 2;
            mapScale.text = 50;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(50 * 1000.0f));
        } else if (mapStatus.zoom < 8.0 && mapStatus.zoom >= 7.0) {
            mapScale.unit = 2;
            mapScale.text = 100;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(100 * 1000.0f));
        } else if (mapStatus.zoom < 7.0 && mapStatus.zoom >= 6.0) {
            mapScale.unit = 2;
            mapScale.text = 200;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(200 * 1000.0f));
        } else if (mapStatus.zoom < 6.0 && mapStatus.zoom >= 5.0) {
            mapScale.unit = 2;
            mapScale.text = 500;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(500 * 1000.0f));
        } else if (mapStatus.zoom < 5.0) {
            mapScale.unit = 2;
            mapScale.text = 1000;
            mapScale.width = (int) (mBDMap.getProjection().metersToEquatorPixels(1000 * 1000.0f));
        }
        return mapScale;
    }

    @Override
    public void setIndoorMapFloor(MapIndoorInfo mapIndoorInfo) {

        Log.i(TAG, "setIndoorMapFloor floor=" + mapIndoorInfo.activeFloorName + " id=" + mapIndoorInfo.poiid);
        mBDMap.switchBaseIndoorMapFloor(mapIndoorInfo.activeFloorName, mapIndoorInfo.poiid);
    }

    @Override
    public void setOnMapClickListener(final OnMapClickListener mapClickListener) {

        mBDMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mapClickListener.onMapClick();
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                mapClickListener.onMapClick();
            }
        });
    }

    @Override
    public void setOnMarkerClickListener(final OnMarkerClickListener markerClickListener) {

        mBDMap.setOnMarkerClickListener(marker -> markerClickListener.onMarkerClick(marker.getId()));
    }

    @Override
    public void setOnCameraChangeListener(final OnCameraChangeListener onCameraChangeListener) {

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

    @Override
    public void setOnIndoorMapListener(final OnIndoorMapListener onIndoorMapListener) {

        mBDMap.setOnBaseIndoorMapListener((b, mapBaseIndoorMapInfo) -> {

            if (b && mapBaseIndoorMapInfo != null) {
                MapIndoorInfo mapIndoorInfo = new MapIndoorInfo();
                mapIndoorInfo.activeFloorName = mapBaseIndoorMapInfo.getCurFloor();
                mapIndoorInfo.poiid = mapBaseIndoorMapInfo.getID();
                mapIndoorInfo.floor_names = mapBaseIndoorMapInfo.getFloors().toArray(new String[0]);
                onIndoorMapListener.onIndoormapInfo(true, mapIndoorInfo);
            } else {
                onIndoorMapListener.onIndoormapInfo(false, null);
            }
        });
    }
}
