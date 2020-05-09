package com.ishare.mapadapter.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MXMarker {

    private Context context;
    public MXMarker(Context context) {
        this.context = context;
    }

    /*------------------------------------ params start ----------------------------------*/

    public MXLatLng mxLatLng;
    public MXMarker setMxLatLng(MXLatLng mxLatLng){
        this.mxLatLng=mxLatLng;
        return this;
    }

    public String title;
    public MXMarker setTitle(String title){
        this.title=title;
        return this;
    }

    public Bitmap bitmap;
    public MXMarker setBitmap(Bitmap bitmap){
        this.bitmap=bitmap;
        return this;
    }
    public MXMarker setResId(int resId){
        this.bitmap = BitmapFactory.decodeStream(context.getResources().openRawResource(resId));
        return this;
    }

    public Bitmap[] bitmapArray;
    public MXMarker setBitmapArray(Bitmap[] bitmapArray){
        this.bitmapArray=bitmapArray;
        return this;
    }
    public MXMarker setResIdArray(int[] resIdArray){
        this.bitmapArray=new Bitmap[resIdArray.length];
        for(int i=0;i<resIdArray.length;i++){
            this.bitmapArray[i]=BitmapFactory.decodeStream(context.getResources().openRawResource(resIdArray[i]));
        }
        return this;
    }

    public boolean isFlat;
    public MXMarker setIsFlat(boolean isFlat){
        this.isFlat=isFlat;
        return this;
    }

    public float zIndex;
    public MXMarker setzIndex(float zIndex){
        this.zIndex=zIndex;
        return this;
    }

    public int period;
    public MXMarker setPeriod(int period) {
        this.period = period;
        return this;
    }

    public float anchorU = 0.5F;
    public float anchorV = 1.0F;
    public MXMarker setAnchor(float anchorU, float anchorV) {
        this.anchorU = anchorU;
        this.anchorV = anchorV;
        return this;
    }

    public float rotateAngle;
    public MXMarker setRotateAngle(float rotateAngle) {
        this.rotateAngle = rotateAngle;
        return this;
    }

    /*------------------------------------ params end ----------------------------------*/

    public Object mapMarker;
    public String mapMarkerId;
}
