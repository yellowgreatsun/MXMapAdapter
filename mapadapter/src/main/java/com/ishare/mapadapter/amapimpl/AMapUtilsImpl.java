package com.ishare.mapadapter.amapimpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.ishare.mapadapter.R;
import com.ishare.mapadapter.api.MXMapUtils;
import com.ishare.mapadapter.bean.MXLatLng;

import java.util.ArrayList;
import java.util.List;

public class AMapUtilsImpl implements MXMapUtils {


    @Override
    public float calculateArea(MXLatLng leftTopMxLatlng, MXLatLng rightBottomMxLatlng) {

        LatLng leftTopLatlng=new LatLng(leftTopMxLatlng.latitudeGCJ02, leftTopMxLatlng.longitudeGCJ02);
        LatLng rightBottomLatlng=new LatLng(rightBottomMxLatlng.latitudeGCJ02, rightBottomMxLatlng.longitudeGCJ02);
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
    public float calculateLineDistance(MXLatLng startMxLatlng, MXLatLng endMxLatlng) {

        LatLng startLatlng=new LatLng(startMxLatlng.latitudeGCJ02, startMxLatlng.longitudeGCJ02);
        LatLng endLatlng=new LatLng(endMxLatlng.latitudeGCJ02, endMxLatlng.longitudeGCJ02);
        return AMapUtils.calculateLineDistance(startLatlng,endLatlng);
    }

    static BitmapDescriptor getMapBitmapDescriptor(Context context, String title, Bitmap bitmap) {

        View markerView = LayoutInflater.from(context).inflate(
                R.layout.layout_marker, null);
        TextView textView = markerView.findViewById(R.id.marker_title);
        ImageView markerImage = markerView.findViewById(R.id.marker_image);
        textView.setText(title);
        markerImage.setBackground(new BitmapDrawable(context.getResources(), bitmap));
        if (TextUtils.isEmpty(title))
            textView.setVisibility(View.GONE);
        return BitmapDescriptorFactory.fromView(markerView);
    }
}
