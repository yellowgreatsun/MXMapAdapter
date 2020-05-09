package com.ishare.mapadapter.bean;

import android.content.Context;

import com.ishare.mapadapter.MapConstant;
import com.ishare.mapadapter.utils.CoordinateConvertUtils2;

public class MXLatLng {

    public double latitudeGCJ02;
    public double longitudeGCJ02;

    public double latitudeBD09;
    public double longitudeBD09;

    public double latitudeWGS84;
    public double longitudeWGS84;

    public MXLatLng(Context context,double latitude, double longitude, MapConstant.CoordinateType coordinatetype) {

        double[] latLngGCJ02, latLngBD09, latLngWGS84;

        switch (coordinatetype) {
            case COORDINATE_GCJ02:
                this.latitudeGCJ02 = latitude;
                this.longitudeGCJ02 = longitude;
                latLngBD09 = CoordinateConvertUtils2.convertGCJ02ToBD09(latitude, longitude);
                this.latitudeBD09 = latLngBD09[0];
                this.longitudeBD09 = latLngBD09[1];
                latLngWGS84 = CoordinateConvertUtils2.convertGCJ02ToWGS84(latitude, longitude);
                this.latitudeWGS84 = latLngWGS84[0];
                this.longitudeWGS84 = latLngWGS84[1];
                break;
            case COORDINATE_BD09:
                this.latitudeBD09 = latitude;
                this.longitudeBD09 = longitude;
                latLngGCJ02 = CoordinateConvertUtils2.convertBD09ToGCJ02(latitude, longitude);
                this.latitudeGCJ02 = latLngGCJ02[0];
                this.longitudeGCJ02 = latLngGCJ02[1];
                latLngWGS84 = CoordinateConvertUtils2.convertBD09ToWGS84(latitude, longitude);
                this.latitudeWGS84 = latLngWGS84[0];
                this.longitudeWGS84 = latLngWGS84[1];
                break;
            case COORDINATE_WGS84:
                latLngGCJ02 = CoordinateConvertUtils2.convertWGS84ToGCJ02(latitude, longitude);
                this.latitudeBD09 = latLngGCJ02[0];
                this.longitudeBD09 = latLngGCJ02[1];
                latLngBD09 = CoordinateConvertUtils2.convertWGS84ToBD09(latitude, longitude);
                this.latitudeBD09 = latLngBD09[0];
                this.longitudeBD09 = latLngBD09[1];
                this.latitudeWGS84 = latitude;
                this.longitudeWGS84 = longitude;
                break;
            default:
                this.latitudeGCJ02 = latitude;
                this.longitudeGCJ02 = longitude;
                latLngBD09 = CoordinateConvertUtils2.convertGCJ02ToBD09(latitude, longitude);
                this.latitudeBD09 = latLngBD09[0];
                this.longitudeBD09 = latLngBD09[1];
                latLngWGS84 = CoordinateConvertUtils2.convertGCJ02ToWGS84(latitude, longitude);
                this.latitudeWGS84 = latLngWGS84[0];
                this.longitudeWGS84 = latLngWGS84[1];
                break;
        }
    }

    public MXLatLng(double latitudeGCJ02, double longitudeGCJ02,
                    double latitudeBD09, double longitudeBD09,
                    double latitudeWGS84, double longitudeWGS84) {

        this.latitudeGCJ02 = latitudeGCJ02;
        this.longitudeGCJ02 = longitudeGCJ02;
        this.latitudeBD09 = latitudeBD09;
        this.longitudeBD09 = longitudeBD09;
        this.latitudeWGS84 = latitudeWGS84;
        this.longitudeWGS84 = longitudeWGS84;
    }

    @Override
    public String toString() {
        return "MXLatLng{" +
                "latitudeGCJ02=" + latitudeGCJ02 +
                ", longitudeGCJ02=" + longitudeGCJ02 +
                ", latitudeBD09=" + latitudeBD09 +
                ", longitudeBD09=" + longitudeBD09 +
                ", latitudeWGS84=" + latitudeWGS84 +
                ", longitudeWGS84=" + longitudeWGS84 +
                '}';
    }
}
