package com.ishare.mapadapter.amapimpl;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ishare.mapadapter.api.MXLocationClient;
import com.ishare.mapadapter.bean.MXLatLng;

public class AMapLocationClientImpl implements MXLocationClient,AMapLocationListener {

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationClientOption;

    @Override
    public void init(Context context) {

        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(context);
            mLocationClientOption = new AMapLocationClientOption();
            mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);  //设置为高精度定位模式
            mLocationClientOption.setInterval(2000);  // 设置发送定位请求的时间间隔,最小值为2000，如果小于2000，按照2000算
            mLocationClientOption.setOnceLocation(false);  //是否单次定位
            mLocationClientOption.setNeedAddress(true);  //设置是否返回地址信息（默认返回地址信息）
            mLocationClientOption.setLocationCacheEnable(false);  //关闭缓存机制
            mLocationClient.setLocationOption(mLocationClientOption);  // 设置定位参数
        } else if (!mLocationClient.isStarted()) {
            mLocationClient.setLocationOption(mLocationClientOption);  // 设置定位参数
        }
    }

    @Override
    public void startLocation() {

        if (mLocationClient != null) {
            mLocationClient.stopLocation();  //最好调用一次stop，再调用start以保证生效
            mLocationClient.startLocation();
        }
    }

    @Override
    public void stopLocation() {

        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(this);
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    private OnLocationChangeListener onLocationChangeListener ;
    @Override
    public void setOnLocationChangeListener(final OnLocationChangeListener onLocationChangeListener) {

        this.onLocationChangeListener = onLocationChangeListener;
        if (mLocationClient != null)
            mLocationClient.setLocationListener(this);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

//        amapLocation.getLocationType();//获取当前定位结果来源 0 fail；1 gps；2 last；4 cache; 5 wifi; 6 cell; 8 offline
//        amapLocation.getLatitude();//获取纬度
//        amapLocation.getLongitude();//获取经度
//        amapLocation.getAccuracy();//获取精度信息
//        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//        amapLocation.getCountry();//国家信息
//        amapLocation.getProvince();//省信息
//        amapLocation.getCity();//城市信息
//        amapLocation.getDistrict();//城区信息
//        amapLocation.getStreet();//街道信息
//        amapLocation.getStreetNum();//街道门牌号信息
//        amapLocation.getCityCode();//城市编码
//        amapLocation.getAdCode();//地区编码
//        amapLocation.getAoiName();//获取当前定位点的AOI信息
//        amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
//        amapLocation.getFloor();//获取当前室内定位的楼层
//        amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
        MXLatLng mxLatLng = new MXLatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude(), 0, 0);
        onLocationChangeListener.onLocationChanged(mxLatLng);
    }
}
