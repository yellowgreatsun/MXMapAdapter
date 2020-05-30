package com.ishare.mapadapter.bean;

import android.content.Context;

import java.util.List;

public class MXPolyline {

    private Context context;
    public MXPolyline(Context context) {
        this.context = context;
    }

    /*------------------------------------ params start ----------------------------------*/

    public List<MXLatLng> mxLatLngList;
    public MXPolyline setMXLatLngList(List<MXLatLng> mxLatLngList) {
        this.mxLatLngList = mxLatLngList;
        return this;
    }

    public int colorId;
    public MXPolyline setColorId(int colorId) {
        this.colorId = colorId;
        return this;
    }

    public float width;  //兼容高德、百度地图时，以高德为准
    public MXPolyline setWidth(float width) {
        this.width = width;
        return this;
    }

    /*------------------------------------ params end ----------------------------------*/

    public Object mapPolyline;
}
