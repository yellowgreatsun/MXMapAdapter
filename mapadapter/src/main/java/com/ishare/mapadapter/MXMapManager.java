package com.ishare.mapadapter;

import android.content.Context;

import com.ishare.mapadapter.amapimpl.AMapFragmentImpl;
import com.ishare.mapadapter.api.MXMapFragment;
import com.ishare.mapadapter.api.MXMapUtils;
import com.ishare.mapadapter.api.MXMapView;
import com.ishare.mapadapter.amapimpl.AMapImpl;
import com.ishare.mapadapter.amapimpl.AMapUtilsImpl;
import com.ishare.mapadapter.amapimpl.AMapViewImpl;
import com.ishare.mapadapter.bdmapimpl.BDMapFragmentImpl;
import com.ishare.mapadapter.bdmapimpl.BDMapImpl;
import com.ishare.mapadapter.bdmapimpl.BDMapUtilsImpl;
import com.ishare.mapadapter.bdmapimpl.BDMapViewImpl;
import com.ishare.mapadapter.googlemapimpl.GoogleMapFragmentImpl;
import com.ishare.mapadapter.googlemapimpl.GoogleMapImpl;
import com.ishare.mapadapter.googlemapimpl.GoogleMapUtilsImpl;
import com.ishare.mapadapter.googlemapimpl.GoogleMapViewImpl;

public class MXMapManager {

    private static MXMapManager instance;

    private MXMapManager() {
    }

    public synchronized static MXMapManager getInstance() {

        if (instance == null) {
            instance = new MXMapManager();
        }
        return instance;
    }

    /**
     * 根据地图类型初始化地图
     * @param context  context信息，传入ApplicationContext
     * @param mapProvider  地图类型，默认AMap
     */
    public void initAppMapManager(Context context, MapConstant.MapProvider mapProvider){

        switch (mapProvider){
            case AMAP:
                AMapImpl.initAppMapManager(context);
                break;
            case BDMAP:
                BDMapImpl.initAppMapManager(context);
                break;
            case GOOGLEMAP:
                GoogleMapImpl.initAppMapManager(context);
                break;
            default:
                AMapImpl.initAppMapManager(context);
                break;
        }
    }

    /**
     * 根据地图类型初始化地图
     * @param context  context信息，传入Activity context
     * @param sdcardDir 路径
     * @param mapProvider  地图类型，默认AMap
     */
    public void initActivityMapManager(Context context, String sdcardDir, MapConstant.MapProvider mapProvider){

        switch (mapProvider){
            case AMAP:
                AMapImpl.initActivityMapManager(context,sdcardDir);
                break;
            case BDMAP:
                BDMapImpl.initActivityMapManager(context,sdcardDir);
                break;
            case GOOGLEMAP:
                GoogleMapImpl.initActivityMapManager(context,sdcardDir);
                break;
            default:
                AMapImpl.initActivityMapManager(context,sdcardDir);
                break;
        }
    }

    /**
     * 根据地图类型获取地图View控制器
     * @param mapProvider  地图类型，默认AMap
     * @return 地图View控制器
     */
    public MXMapView getMXMapView(MapConstant.MapProvider mapProvider) {

        MXMapView mMXMapView;
        switch (mapProvider) {
            case AMAP:
                mMXMapView = new AMapViewImpl();
                break;
            case BDMAP:
                mMXMapView = new BDMapViewImpl();
                break;
            case GOOGLEMAP:
                mMXMapView = new GoogleMapViewImpl();
                break;
            default:
                mMXMapView = new AMapViewImpl();
                break;
        }
        return mMXMapView;
    }

    /**
     * 根据地图类型获取地图Fragment管理器
     * @param mapProvider  地图类型，默认AMap
     * @return 地图View控制器
     */
    public MXMapFragment getMXMapFragment(MapConstant.MapProvider mapProvider) {

        MXMapFragment mMXMapView;
        switch (mapProvider) {
            case AMAP:
                mMXMapView = new AMapFragmentImpl();
                break;
            case BDMAP:
                mMXMapView = new BDMapFragmentImpl();
                break;
            case GOOGLEMAP:
                mMXMapView = new GoogleMapFragmentImpl();
                break;
            default:
                mMXMapView = new AMapFragmentImpl();
                break;
        }
        return mMXMapView;
    }

    /**
     * 根据地图类型获取地图工具类
     * @param mapProvider  地图类型，默认AMap
     * @return 地图工具类
     */
    public MXMapUtils getMXMapUtils(MapConstant.MapProvider mapProvider) {

        MXMapUtils mMXMapUtils;
        switch (mapProvider) {
            case AMAP:
                mMXMapUtils = new AMapUtilsImpl();
                break;
            case BDMAP:
                mMXMapUtils = new BDMapUtilsImpl();
                break;
            case GOOGLEMAP:
                mMXMapUtils = new GoogleMapUtilsImpl();
                break;
            default:
                mMXMapUtils = new AMapUtilsImpl();
                break;
        }
        return mMXMapUtils;
    }
}
