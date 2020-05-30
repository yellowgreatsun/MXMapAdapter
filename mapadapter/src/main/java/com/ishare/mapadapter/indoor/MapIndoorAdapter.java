/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.ishare.mapadapter.indoor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 楼层条数据适配器
 */
public class MapIndoorAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private String[] mFloorList = new String[]{};
    private int selectedPos;
    private Context mContext;

    private static class NoteViewHolder {

        private TextView mFloorTextTV;
    }

    public MapIndoorAdapter(Context ctx) {
        mInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = ctx;
    }

    public void setmFloorList(String[] mFloorList) {
        this.mFloorList = mFloorList;
    }

    public void setNoteList(String[] floorList) {
        mFloorList = floorList;
    }

    public int getCount() {
        return mFloorList.length;
    }

    public Object getItem(int position) {
        return mFloorList[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public void setSelectedPostion(int postion) {
        selectedPos = postion;
    }

    public int getPostionFromFloor(String floor) {

        int pos = -1;
        for (int i = 0; i < getCount(); i++) {
            if (floor.equalsIgnoreCase(mFloorList[i])) {
                pos = i;
                break;
            }
        }
        return mFloorList.length - 1 - pos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        NoteViewHolder holder;
        if (convertView == null) {
            convertView = new MapIndoorItem(mContext);
            holder = new NoteViewHolder();
            holder.mFloorTextTV = ((MapIndoorItem) convertView).getmText();
            convertView.setTag(holder);
        } else {
            holder = (NoteViewHolder) convertView.getTag();
        }

        String floor = mFloorList[mFloorList.length - 1 - position];
        if (floor != null) {
            holder.mFloorTextTV.setText(floor);
        }
        if (selectedPos == position) {
            refreshViewStyle(holder.mFloorTextTV, true);
        } else {
            refreshViewStyle(holder.mFloorTextTV, false);
        }
        return convertView;
    }

    private void refreshViewStyle(TextView view, boolean isSelected) {

        if (isSelected) {
            view.setBackgroundColor(MapIndoorItem.colorSelected);
        } else {
            view.setBackgroundColor(MapIndoorItem.color);
        }
        view.setSelected(isSelected);
    }
}

