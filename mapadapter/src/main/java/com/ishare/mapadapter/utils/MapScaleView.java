package com.ishare.mapadapter.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;


public class MapScaleView extends View {

    private enum ScaleType {
        METERS_ONLY, MILES_ONLY, BOTH
    }

    private ScaleType scaleType = ScaleType.METERS_ONLY;
    private final MapScaleModel mapScaleModel;
    private final int desiredWidth;
    private final Paint paint = new Paint();
    private final Paint strokePaint = new Paint();
    private final Path strokePath = new Path();

    private float textHeight;
    private Scales scales;

    private float horizontalLineY;

    public MapScaleView(Context context) {
        this(context,null);
    }

    public MapScaleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MapScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        ViewConfig viewConfig = new ViewConfig(context, attrs);
        float density = getResources().getDisplayMetrics().density;
        mapScaleModel = new MapScaleModel(density, context);
        if (viewConfig.isMiles) {
            scaleType = ScaleType.MILES_ONLY;
        }

        desiredWidth = viewConfig.desiredWidth;
        int defaultColor = viewConfig.color;

        paint.setAntiAlias(true);
        paint.setColor(defaultColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(viewConfig.textSize);

        strokePaint.setAntiAlias(true);
        strokePaint.setColor(defaultColor);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(viewConfig.strokeWidth);

        updateTextHeight();
    }

    private void updateTextHeight() {
        Rect textRect = new Rect();
        paint.getTextBounds("A", 0, 1, textRect);
        textHeight = textRect.height();
        horizontalLineY = textHeight + textHeight / 2;
    }

    public void setColor(@ColorInt int color) {
        paint.setColor(color);
        strokePaint.setColor(color);
        invalidate();
    }

    public void setTextSize(float textSize) {
        paint.setTextSize(textSize);
        updateTextHeight();
        invalidate();
    }

    public void setStrokeWidth(float strokeWidth) {
        strokePaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    /**
     * @deprecated Use milesOnly()
     */
    @Deprecated
    public void setIsMiles(boolean miles) {
        if (miles) milesOnly();
        else metersAndMiles();
    }

    public void metersOnly() {
        scaleType = ScaleType.METERS_ONLY;
        updateScales();
    }

    public void milesOnly() {
        scaleType = ScaleType.MILES_ONLY;
        updateScales();
    }

    public void metersAndMiles() {
        scaleType = ScaleType.BOTH;
        updateScales();
    }

    public void update(float zoom, double latitude) {
        mapScaleModel.setPosition(zoom, latitude);
        updateScales();
    }

    private void updateScales() {

        Scale top, bottom = null;
        if (scaleType == ScaleType.MILES_ONLY) {
            top = mapScaleModel.update(false);
        } else {
            top = mapScaleModel.update(true);
            if (scaleType == ScaleType.BOTH) {
                bottom = mapScaleModel.update(false);
            }
        }

        scales = new Scales(top, bottom);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(desiredWidth(), widthMeasureSpec);
        int height = measureDimension(desiredHeight(), heightMeasureSpec);

        mapScaleModel.setMaxWidth(width);
        updateScales();

        setMeasuredDimension(width, height);
    }

    private int desiredWidth() {
        return desiredWidth;
    }

    private int desiredHeight() {
        float density = getResources().getDisplayMetrics().density;
        return (int) (18 * density);
    }

    private int measureDimension(int desiredSize, int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            return size;
        } else if (mode == MeasureSpec.AT_MOST) {
            return Math.min(desiredSize, size);
        } else {
            return desiredSize;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (scales == null || scales.top() == null) {
            return;
        }

        Scale top = scales.top();
        canvas.drawText(top.text(), 0, textHeight, paint);

        strokePath.rewind();
        strokePath.moveTo(0, textHeight);
        strokePath.lineTo(0, horizontalLineY);
        strokePath.lineTo(top.length(), horizontalLineY);
        strokePath.lineTo(top.length(), textHeight);

        Scale bottom = scales.bottom();
        if (bottom != null) {

            if (bottom.length() > top.length()) {
                strokePath.moveTo(top.length(), horizontalLineY);
                strokePath.lineTo(bottom.length(), horizontalLineY);
            } else {
                strokePath.moveTo(bottom.length(), horizontalLineY);
            }

            strokePath.lineTo(bottom.length(), textHeight * 2);

            float bottomTextY = horizontalLineY + textHeight + textHeight / 2;
            canvas.drawText(bottom.text(), 0, bottomTextY, paint);
        }

        canvas.drawPath(strokePath, strokePaint);
    }

    class ViewConfig {

        final int desiredWidth;

        final int color;
        final float textSize;
        final float strokeWidth;
        final boolean isMiles;

        ViewConfig(Context context, AttributeSet attrs) {

            //float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            float density = context.getResources().getDisplayMetrics().density;
            desiredWidth = (int) (40 * density);
            color = Color.parseColor("#ff202726");
            textSize = 9f * density;
            strokeWidth = 1f * density;
            isMiles = false;
        }
    }

    class Scales {
        private final Scale top, bottom;

        Scales(Scale top, Scale bottom) {
            this.top = top;
            this.bottom = bottom;
        }

        public Scale top() {
            return top;
        }

        public Scale bottom() {
            return bottom;
        }
    }
}
