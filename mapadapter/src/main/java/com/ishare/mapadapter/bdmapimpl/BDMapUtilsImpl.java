package com.ishare.mapadapter.bdmapimpl;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.AreaUtil;
import com.baidu.mapapi.utils.DistanceUtil;
import com.ishare.mapadapter.api.MXMapUtils;
import com.ishare.mapadapter.bean.MXLatLng;

import java.util.List;

public class BDMapUtilsImpl implements MXMapUtils {


    @Override
    public float calculateArea(MXLatLng leftTopMXLatlng, MXLatLng rightBottomMXLatlng) {

        LatLng leftTopLatlng = new LatLng(leftTopMXLatlng.latitudeBD09, leftTopMXLatlng.longitudeBD09);
        LatLng rightBottomLatlng = new LatLng(rightBottomMXLatlng.latitudeBD09, rightBottomMXLatlng.longitudeBD09);
        return (float) AreaUtil.calculateArea(leftTopLatlng, rightBottomLatlng);
    }

    @Override
    public float calculateArea(List<MXLatLng> mxPoints) {

        return 0;
    }

    @Override
    public float calculateLineDistance(MXLatLng startMXLatlng, MXLatLng endMXLatlng) {

        LatLng startLatlng = new LatLng(startMXLatlng.latitudeBD09, startMXLatlng.longitudeBD09);
        LatLng endLatlng = new LatLng(endMXLatlng.latitudeBD09, endMXLatlng.longitudeBD09);
        return (float) DistanceUtil.getDistance(startLatlng, endLatlng);
    }
}
