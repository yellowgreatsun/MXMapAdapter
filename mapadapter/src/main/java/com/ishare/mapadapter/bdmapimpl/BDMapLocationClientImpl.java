package com.ishare.mapadapter.bdmapimpl;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ishare.mapadapter.api.MXLocationClient;
import com.ishare.mapadapter.bean.MXLatLng;

public class BDMapLocationClientImpl extends BDAbstractLocationListener implements MXLocationClient {

    private static final String TAG = "BDMapLocationClientImpl";

    LocationClient mBaiduLocationClient;
    LocationClientOption mBaiduLocationClientOption;

    @Override
    public void init(Context context) {

        if (mBaiduLocationClient == null) {
            mBaiduLocationClient = new LocationClient(context);
            mBaiduLocationClientOption = new LocationClientOption();
            mBaiduLocationClientOption.setOpenGps(true); // 打开gps
            mBaiduLocationClientOption.setCoorType("bd09ll"); // 设置坐标类型
            mBaiduLocationClientOption.setIsNeedLocationDescribe(true);
            mBaiduLocationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            mBaiduLocationClientOption.setOpenAutoNotifyMode();
            mBaiduLocationClient.setLocOption(mBaiduLocationClientOption);
            mBaiduLocationClient.start();
        } else if (!mBaiduLocationClient.isStarted()) {
            mBaiduLocationClient.setLocOption(mBaiduLocationClientOption);
        }
    }

    @Override
    public void startLocation() {

        if (mBaiduLocationClient != null) {

            mBaiduLocationClient.stop();
            mBaiduLocationClient.start();
        }
    }

    @Override
    public void stopLocation() {

        if (mBaiduLocationClient != null) {
            mBaiduLocationClient.unRegisterLocationListener(this);
            mBaiduLocationClient.stop();
        }
    }

    private OnLocationChangeListener onLocationChangeListener;
    @Override
    public void setOnLocationChangeListener(final OnLocationChangeListener onLocationChangeListener) {

        this.onLocationChangeListener = onLocationChangeListener;
        if (mBaiduLocationClient != null)
            mBaiduLocationClient.registerLocationListener(this);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

        MXLatLng mxLatLng = new MXLatLng(0, 0, bdLocation.getLatitude(), bdLocation.getLongitude());
        Log.i(TAG, "onReceiveLocation:" + bdLocation.toString());
        onLocationChangeListener.onLocationChanged(mxLatLng);
    }
}
