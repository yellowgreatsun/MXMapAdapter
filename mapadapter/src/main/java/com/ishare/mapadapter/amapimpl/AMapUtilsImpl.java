package com.ishare.mapadapter.amapimpl;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.ishare.mapadapter.api.MXMapUtils;
import com.ishare.mapadapter.bean.MXLatLng;

import java.util.ArrayList;
import java.util.List;

public class AMapUtilsImpl implements MXMapUtils {


    @Override
    public float calculateArea(MXLatLng leftTopMXLatlng, MXLatLng rightBottomMXLatlng) {

        LatLng leftTopLatlng=new LatLng(leftTopMXLatlng.latitudeGCJ02,leftTopMXLatlng.longitudeGCJ02);
        LatLng rightBottomLatlng=new LatLng(rightBottomMXLatlng.latitudeGCJ02,rightBottomMXLatlng.longitudeGCJ02);
        return AMapUtils.calculateArea(leftTopLatlng,rightBottomLatlng);
    }

    @Override
    public float calculateArea(List<MXLatLng> mxPoints) {

        List<LatLng> points = new ArrayList<>();
        for (MXLatLng mxPoint : mxPoints) {
            points.add(new LatLng(mxPoint.latitudeGCJ02, mxPoint.longitudeGCJ02));
        }
        return AMapUtils.calculateArea(points);
    }

    @Override
    public float calculateLineDistance(MXLatLng startMXLatlng, MXLatLng endMXLatlng) {

        LatLng startLatlng=new LatLng(startMXLatlng.latitudeGCJ02,startMXLatlng.longitudeGCJ02);
        LatLng endLatlng=new LatLng(endMXLatlng.latitudeGCJ02,endMXLatlng.longitudeGCJ02);
        return AMapUtils.calculateLineDistance(startLatlng,endLatlng);
    }
}
