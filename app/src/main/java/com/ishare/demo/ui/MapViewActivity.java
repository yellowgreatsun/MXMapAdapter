package com.ishare.demo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ishare.demo.Constant;
import com.ishare.mapadapter.MXMapManager;
import com.ishare.mapadapter.R;
import com.ishare.mapadapter.api.MXMap;
import com.ishare.mapadapter.api.MXMapView;
import com.ishare.mapadapter.bean.MXLatLng;
import com.ishare.mapadapter.bean.MXMarker;
import com.ishare.mapadapter.bean.MXPolyline;

import java.util.ArrayList;
import java.util.List;

public class MapViewActivity extends AppCompatActivity {

    private static final String TAG = "MapViewActivity";

    MXMapView mMXMapView;
    MXMap mMXMap;

    ViewGroup llParent;
    MXMarker mxMarker;
    MXMarker animateMxMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        initView();
        initMap(savedInstanceState);
    }

    private void initView() {

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        llParent = findViewById(R.id.ll_parent);
    }

    private void initMap(Bundle savedInstanceState) {

        mMXMapView = MXMapManager.getInstance().getMXMapView(Constant.MAP_PROVIDER);
        View mapView = mMXMapView.createMapView(this);
        mMXMapView.onCreate(this, savedInstanceState);
        mMXMapView.createMap(mxMap -> {
            mMXMap = mxMap;
            initMapSetting();
            initListener();
        });
        llParent.addView(mapView);
    }

    private void initMapSetting() {

        mMXMap.uiSetting();
        mMXMap.setMapType(Constant.MAP_TYPE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addMarker:
                addMarker();
                break;
            case R.id.updateMarker:
                updateMarker();
                break;
            case R.id.addMarkerGif:
                addMarkerGif();
                break;
            case R.id.addMarkers:
                addMarkers();
                break;
            case R.id.addPolyLine:
                addPolyLine();
                break;
            case R.id.clear:
                clear();
                break;
            case R.id.mapUtilsTest:
                mapUtilsTest();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addMarker() {

        mMXMap.clear();

        int resId = R.mipmap.location;
        MXLatLng mxLatLng = new MXLatLng(MapViewActivity.this, 39.761, 116.434, Constant.COORDINATE_TYPE);
        mxMarker = new MXMarker(this).setMxLatLng(mxLatLng).setResId(resId).setAnchor(0.5f, 0.65f).setzIndex(0f).setPeriod(10).setIsFlat(true);
        String markerId = mMXMap.addMarker(mxMarker);
        Log.i(TAG, "markerId=" + markerId);

        mMXMap.animateCamera(mxLatLng, 16, 500);
    }

    private void addMarkerGif() {

        mMXMap.clear();

        int[] reaIdArray = new int[]{R.mipmap.location1, R.mipmap.location2, R.mipmap.location3, R.mipmap.location4,
                R.mipmap.location5, R.mipmap.location6, R.mipmap.location7, R.mipmap.location8};
        MXLatLng animateMxLatLng = new MXLatLng(MapViewActivity.this, 39.755, 116.434, Constant.COORDINATE_TYPE);
        animateMxMarker = new MXMarker(this).setMxLatLng(animateMxLatLng).setResIdArray(reaIdArray).setAnchor(0.5f, 0.65f).setzIndex(0f).setPeriod(10).setIsFlat(true);
        String animateMarkerId = mMXMap.addMarker(animateMxMarker, true);
        Log.i(TAG, "animateMarkerId=" + animateMarkerId);

        mMXMap.animateCamera(animateMxLatLng, 16, 500);
    }

    private void updateMarker() {

        if (mxMarker == null || mxMarker.mapMarker == null)
            return;

        MXLatLng mxLatLng = new MXLatLng(MapViewActivity.this, 39.771, 116.434, Constant.COORDINATE_TYPE);
        mxMarker.setMxLatLng(mxLatLng).setResId(R.mipmap.location).setAnchor(0.5f, 0.65f).setzIndex(0f).setPeriod(10).setIsFlat(true);
        mMXMap.updateMarker(mxMarker);

        mMXMap.animateCamera(mxLatLng, 16, 500);
    }

    private void addMarkers() {

        mMXMap.clear();

        List<MXLatLng> mxLatLngList = new ArrayList<>();
        mxLatLngList.add(new MXLatLng(MapViewActivity.this, 39.761, 116.434, Constant.COORDINATE_TYPE));
        mxLatLngList.add(new MXLatLng(MapViewActivity.this, 39.761, 116.834, Constant.COORDINATE_TYPE));
        mxLatLngList.add(new MXLatLng(MapViewActivity.this, 39.561, 116.534, Constant.COORDINATE_TYPE));
        mxLatLngList.add(new MXLatLng(MapViewActivity.this, 39.901, 116.634, Constant.COORDINATE_TYPE));
        mxLatLngList.add(new MXLatLng(MapViewActivity.this, 39.561, 116.734, Constant.COORDINATE_TYPE));

        int resId = R.mipmap.location;
        MXMarker mxMarker1 = new MXMarker(this).setMxLatLng(new MXLatLng(this, 39.761, 116.434, Constant.COORDINATE_TYPE)).setResId(resId).setAnchor(0.5f, 0.65f).setzIndex(0f).setPeriod(10).setIsFlat(true);
        MXMarker mxMarker2 = new MXMarker(this).setMxLatLng(new MXLatLng(this, 39.761, 116.834, Constant.COORDINATE_TYPE)).setResId(resId).setAnchor(0.5f, 0.65f).setzIndex(0f).setPeriod(10).setIsFlat(true);
        MXMarker mxMarker3 = new MXMarker(this).setMxLatLng(new MXLatLng(this, 39.561, 116.534, Constant.COORDINATE_TYPE)).setResId(resId).setAnchor(0.5f, 0.65f).setzIndex(0f).setPeriod(10).setIsFlat(true);
        MXMarker mxMarker4 = new MXMarker(this).setMxLatLng(new MXLatLng(this, 39.901, 116.634, Constant.COORDINATE_TYPE)).setResId(resId).setAnchor(0.5f, 0.65f).setzIndex(0f).setPeriod(10).setIsFlat(true);
        MXMarker mxMarker5 = new MXMarker(this).setMxLatLng(new MXLatLng(this, 39.561, 116.734, Constant.COORDINATE_TYPE)).setResId(resId).setAnchor(0.5f, 0.65f).setzIndex(0f).setPeriod(10).setIsFlat(true);

        List<MXMarker> mxMarkerList = new ArrayList<>();
        mxMarkerList.add(mxMarker1);
        mxMarkerList.add(mxMarker2);
        mxMarkerList.add(mxMarker3);
        mxMarkerList.add(mxMarker4);
        mxMarkerList.add(mxMarker5);
        List<String> markerIdList = mMXMap.addMarkers(mxMarkerList);
        for (String markerId : markerIdList) {
            Log.i(TAG, "markerId=" + markerId);
        }

        mMXMap.animateCamera(mxLatLngList, 500);
    }

    private void addPolyLine() {

        List<MXLatLng> mxLatLngList = new ArrayList<>();
        mxLatLngList.add(new MXLatLng(MapViewActivity.this, 39.761, 116.434, Constant.COORDINATE_TYPE));
        mxLatLngList.add(new MXLatLng(MapViewActivity.this, 39.761, 116.834, Constant.COORDINATE_TYPE));
        mxLatLngList.add(new MXLatLng(MapViewActivity.this, 39.561, 116.534, Constant.COORDINATE_TYPE));
        mxLatLngList.add(new MXLatLng(MapViewActivity.this, 39.901, 116.634, Constant.COORDINATE_TYPE));
        mxLatLngList.add(new MXLatLng(MapViewActivity.this, 39.561, 116.734, Constant.COORDINATE_TYPE));
        mxLatLngList.add(new MXLatLng(MapViewActivity.this, 39.761, 116.434, Constant.COORDINATE_TYPE));

        MXPolyline mxPolyline=new MXPolyline(this).setMxLatLngList(mxLatLngList).setColorId(Color.GREEN).setWidth(10f);
        mMXMap.addPolyline(mxPolyline);

        mMXMap.animateCamera(mxLatLngList, 500);
    }

    private void clear() {

        if (mMXMap != null)
            mMXMap.clear();
    }

    private void initListener() {

        mMXMap.setOnMarkerClickListener(markerId -> {
            Toast.makeText(MapViewActivity.this, markerId, Toast.LENGTH_SHORT).show();
            return true;
        });

        mMXMap.setOnCameraChangeListener(new MXMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(float zoom, double latitude, double longitude) {
                Log.i(TAG, "onCameraChange zoom=" + zoom + " latitude=" + latitude + " longitude=" + longitude);
            }

            @Override
            public void onCameraChangeFinish(float zoom, double latitude, double longitude) {
                Log.i(TAG, "onCameraChangeFinish zoom=" + zoom + " latitude=" + latitude + " longitude=" + longitude);
            }
        });
    }

    private void mapUtilsTest() {

        MXLatLng mxLatLng = new MXLatLng(MapViewActivity.this, 39.761, 116.434, Constant.COORDINATE_TYPE);
        MXLatLng mxLatLng2 = new MXLatLng(MapViewActivity.this, 39.791, 116.434, Constant.COORDINATE_TYPE);
        Log.i(TAG, "calculateLineDistance = " + MXMapManager.getInstance().getMXMapUtils(Constant.MAP_PROVIDER).calculateLineDistance(mxLatLng, mxLatLng2));
    }

    @Override
    protected void onDestroy() {
        mMXMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMXMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMXMapView.onLowMemory();
    }

    @Override
    protected void onPause() {
        mMXMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMXMapView.onResume();
    }
}
