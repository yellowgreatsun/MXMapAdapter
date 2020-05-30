package com.ishare.mapadapter.bdmapimpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.AreaUtil;
import com.baidu.mapapi.utils.DistanceUtil;
import com.ishare.mapadapter.R;
import com.ishare.mapadapter.api.MXMapUtils;
import com.ishare.mapadapter.bean.MXLatLng;

import java.util.List;

public class BDMapUtilsImpl implements MXMapUtils {


    @Override
    public float calculateArea(MXLatLng leftTopXunLatlng, MXLatLng rightBottomXunLatlng) {

        LatLng leftTopLatlng = new LatLng(leftTopXunLatlng.latitudeBD09, leftTopXunLatlng.longitudeBD09);
        LatLng rightBottomLatlng = new LatLng(rightBottomXunLatlng.latitudeBD09, rightBottomXunLatlng.longitudeBD09);
        return (float) AreaUtil.calculateArea(leftTopLatlng, rightBottomLatlng);
    }

    @Override
    public float calculateArea(List<MXLatLng> mxPoints) {

        return 0;
    }

    @Override
    public float calculateLineDistance(MXLatLng startXunLatlng, MXLatLng endXunLatlng) {

        LatLng startLatlng = new LatLng(startXunLatlng.latitudeBD09, startXunLatlng.longitudeBD09);
        LatLng endLatlng = new LatLng(endXunLatlng.latitudeBD09, endXunLatlng.longitudeBD09);
        return (float) DistanceUtil.getDistance(startLatlng, endLatlng);
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
