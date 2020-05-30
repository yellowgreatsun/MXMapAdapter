/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.ishare.mapadapter.indoor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * 楼层条View
 */
public class MapIndoorListView extends ListView {

    public MapIndoorListView(Context context) {
        this(context, null);
    }

    public MapIndoorListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapIndoorListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    public void setStripAdapter(BaseAdapter adapter) {
        super.setAdapter(adapter);

        Log.i("MapIndoorListView","adapter.getCount()="+adapter.getCount());
        if (adapter.getCount() > 5) {
            View item = adapter.getView(0, null, this);
            item.measure(0, 0);
            ViewGroup.LayoutParams layoutParam = (ViewGroup.LayoutParams) getLayoutParams();
            layoutParam.height = (int) (5.5 * item.getMeasuredHeight());
            requestLayout();
            invalidate();
        }
    }

//    ViewGroup.LayoutParams layoutParam;

    private void initView(Context context) {

//        setId(0);
        setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        setVisibility(View.GONE);
        setDividerHeight(0);
        setVerticalScrollBarEnabled(false);
        setScrollingCacheEnabled(false);
        setCacheColorHint(Color.TRANSPARENT);
        setCacheColorHint(0);
        setSelector(new Drawable() {
            @Override
            public void draw(Canvas canvas) {

            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter cf) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.UNKNOWN;
            }
        });
//        layoutParam = new RelativeLayout.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        layoutParam.addRule(RelativeLayout.CENTER_IN_PARENT);
//        layoutParam.setMargins(MapIndoorItem.dip2px(context, 20), 0, 0, 0);
//        setLayoutParams(layoutParam);
    }
}
