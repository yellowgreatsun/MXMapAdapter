package com.ishare.demo;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.ishare.mapadapter.MXMapManager;

public class MapAdapterApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        String CPU_ABI = android.os.Build.CPU_ABI;
        Log.e("HYY","CPU_ABI="+CPU_ABI);
        String[] abis = Build.SUPPORTED_ABIS;
        for (String abi:abis){
            Log.e("HYY","abi="+CPU_ABI);
        }
        MXMapManager.getInstance().initAppMapManager(getApplicationContext(), Constant.MAP_PROVIDER);
    }
}
