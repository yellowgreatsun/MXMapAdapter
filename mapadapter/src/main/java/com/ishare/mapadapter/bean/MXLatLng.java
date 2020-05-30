package com.ishare.mapadapter.bean;

import android.content.Context;

import com.ishare.mapadapter.MapConstant;
import com.ishare.mapadapter.utils.CoordinateConvertUtils;
import com.ishare.mapadapter.utils.CoordinateConvertUtils2;

public class MXLatLng {

    public double latitudeGCJ02;
    public double longitudeGCJ02;

    public double latitudeBD09;
    public double longitudeBD09;

    public MXLatLng(Context context, double latitude, double longitude, MapConstant.CoordinateType coordinatetype) {

        double[] latLngGCJ02, latLngBD09;

        switch (coordinatetype) {
            case COORDINATE_GCJ02:
                this.latitudeGCJ02 = latitude;
                this.longitudeGCJ02 = longitude;
                latLngBD09 = CoordinateConvertUtils.convertGCJ02ToBD09(context, latitude, longitude);
                this.latitudeBD09 = latLngBD09[0];
                this.longitudeBD09 = latLngBD09[1];
                break;
            case COORDINATE_BD09:
                this.latitudeBD09 = latitude;
                this.longitudeBD09 = longitude;
                latLngGCJ02 = CoordinateConvertUtils.convertBD09ToGCJ02(context, latitude, longitude);
                this.latitudeGCJ02 = latLngGCJ02[0];
                this.longitudeGCJ02 = latLngGCJ02[1];
                break;
            case COORDINATE_WGS84:
                latLngGCJ02 = CoordinateConvertUtils.convertWGS84ToGCJ02(context, latitude, longitude);
                this.latitudeGCJ02 = latLngGCJ02[0];
                this.longitudeGCJ02 = latLngGCJ02[1];
                latLngBD09 = CoordinateConvertUtils.convertWGS84ToBD09(context, latitude, longitude);
                this.latitudeBD09 = latLngBD09[0];
                this.longitudeBD09 = latLngBD09[1];
                break;
//            default:
//                this.latitudeGCJ02 = latitude;
//                this.longitudeGCJ02 = longitude;
//                latLngBD09 = CoordinateConvertUtils2.convertGCJ02ToBD09(latitude, longitude);
//                this.latitudeBD09 = latLngBD09[0];
//                this.longitudeBD09 = latLngBD09[1];
//                break;
        }
    }

    public MXLatLng(double latitudeGCJ02, double longitudeGCJ02,
                    double latitudeBD09, double longitudeBD09) {

        this.latitudeGCJ02 = latitudeGCJ02;
        this.longitudeGCJ02 = longitudeGCJ02;
        this.latitudeBD09 = latitudeBD09;
        this.longitudeBD09 = longitudeBD09;
    }

    @Override
    public String toString() {
        return "XunLatLng{" +
                "latitudeGCJ02=" + latitudeGCJ02 +
                ", longitudeGCJ02=" + longitudeGCJ02 +
                ", latitudeBD09=" + latitudeBD09 +
                ", longitudeBD09=" + longitudeBD09 +
                '}';
    }
}
