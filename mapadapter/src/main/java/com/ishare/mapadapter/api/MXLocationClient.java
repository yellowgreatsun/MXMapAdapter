package com.ishare.mapadapter.api;

import android.content.Context;

import com.ishare.mapadapter.bean.MXLatLng;

public interface MXLocationClient {

    // context使用全局context
    void init(Context context);

    void startLocation();

    void stopLocation();

    void setOnLocationChangeListener(OnLocationChangeListener onLocationChangeListener);

    interface OnLocationChangeListener{
        void onLocationChanged(MXLatLng mxLatLng);
    }
}
