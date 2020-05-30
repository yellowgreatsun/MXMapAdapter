package com.ishare.mapadapter.indoor;

import java.util.Arrays;

public class MapIndoorInfo {

    public String activeFloorName;  //当前显示楼层名称，如 F1
    public String poiid;  //室内地图poiid，室内地图唯一标识
    public String[] floor_names;  //室内地图楼层名称数组，如["B2","B1","F1","F2"]

    public int activeFloorIndex;  //当前显示楼层index，如1
    public int[] floor_indexs;  //室内地图楼层数组，如[-2,-1,1,2]

    @Override
    public String toString() {
        return "MapIndoorInfo{" +
                "activeFloorName='" + activeFloorName + '\'' +
                ", poiid='" + poiid + '\'' +
                ", floor_names=" + Arrays.toString(floor_names) +
                ", activeFloorIndex=" + activeFloorIndex +
                ", floor_indexs=" + Arrays.toString(floor_indexs) +
                '}';
    }
}
