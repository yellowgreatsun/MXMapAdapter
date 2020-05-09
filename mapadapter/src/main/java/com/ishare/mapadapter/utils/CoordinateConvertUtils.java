package com.ishare.mapadapter.utils;

import android.content.Context;

/**
 * AMap和BaiduMap自带的坐标转换方法，不过都是只能将其他坐标系转换为自家坐标系
 * 优点：能够精确将其他坐标系转换为自家坐标系
 * 缺点：1>.只能将其他坐标系转换为自家坐标系；2>.调用BaiduMapSDK的坐标系转换方法，必须首先对其初始化
 */
public class CoordinateConvertUtils {

    // 如果调用该接口，需要先对baidusdk初始化 BDMapImpl.initMapManager(context);
    public static double[] convertGCJ02ToBD09(Context context, double latitude, double longitude) {

        com.baidu.mapapi.utils.CoordinateConverter converter = new com.baidu.mapapi.utils.CoordinateConverter();
        converter.from(com.baidu.mapapi.utils.CoordinateConverter.CoordType.COMMON);
        converter.coord(new com.baidu.mapapi.model.LatLng(latitude, longitude));
        com.baidu.mapapi.model.LatLng latLng = converter.convert();
        return new double[]{latLng.latitude, latLng.longitude};
    }

    public static double[] convertBD09ToGCJ02(Context context, double latitude, double longitude) {

        com.amap.api.maps.CoordinateConverter converter = new com.amap.api.maps.CoordinateConverter(context);
        converter.from(com.amap.api.maps.CoordinateConverter.CoordType.BAIDU);
        converter.coord(new com.amap.api.maps.model.LatLng(latitude, longitude));
        com.amap.api.maps.model.LatLng latLng = converter.convert();
        return new double[]{latLng.latitude, latLng.longitude};
    }

    public static double[] convertWGS84ToBD09(Context context, double latitude, double longitude) {

        com.baidu.mapapi.utils.CoordinateConverter converter = new com.baidu.mapapi.utils.CoordinateConverter();
        converter.from(com.baidu.mapapi.utils.CoordinateConverter.CoordType.GPS);
        converter.coord(new com.baidu.mapapi.model.LatLng(latitude, longitude));
        com.baidu.mapapi.model.LatLng latLng = converter.convert();
        return new double[]{latLng.latitude, latLng.longitude};
    }

    public static double[] convertWGS84ToGCJ02(Context context, double latitude, double longitude) {

        com.amap.api.maps.CoordinateConverter converter = new com.amap.api.maps.CoordinateConverter(context);
        converter.from(com.amap.api.maps.CoordinateConverter.CoordType.GPS);
        converter.coord(new com.amap.api.maps.model.LatLng(latitude, longitude));
        com.amap.api.maps.model.LatLng latLng = converter.convert();
        return new double[]{latLng.latitude, latLng.longitude};
    }
}
