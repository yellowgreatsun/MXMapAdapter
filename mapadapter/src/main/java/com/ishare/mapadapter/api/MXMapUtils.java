package com.ishare.mapadapter.api;

import com.ishare.mapadapter.bean.MXLatLng;

public interface MXMapUtils {

    float calculateArea(MXLatLng leftTopLatlng, MXLatLng rightBottomLatlng);

    float calculateArea(java.util.List<MXLatLng> points);

    float calculateLineDistance(MXLatLng startLatlng, MXLatLng endLatlng);
}
