package com.ishare.mapadapter.googlemapimpl;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.ishare.mapadapter.api.MXMapUtils;
import com.ishare.mapadapter.bean.MXLatLng;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapUtilsImpl implements MXMapUtils {

    @Override
    public float calculateArea(MXLatLng leftTopLatlng, MXLatLng rightBottomLatlng) {
        return 0;
    }

    @Override
    public float calculateArea(List<MXLatLng> mxPoints) {

        List<LatLng> points = new ArrayList<>();
        for (MXLatLng mxPoint : mxPoints) {
            points.add(new LatLng(mxPoint.latitudeWGS84, mxPoint.longitudeWGS84));
        }
        return (float) SphericalUtil.computeArea(points);
    }

    @Override
    public float calculateLineDistance(MXLatLng startMXLatlng, MXLatLng endMXLatlng) {

        LatLng startLatlng = new LatLng(startMXLatlng.latitudeWGS84, endMXLatlng.longitudeWGS84);
        LatLng endLatlng = new LatLng(startMXLatlng.latitudeWGS84, endMXLatlng.longitudeWGS84);
        return (float) SphericalUtil.computeDistanceBetween(startLatlng, endLatlng);
    }
}
