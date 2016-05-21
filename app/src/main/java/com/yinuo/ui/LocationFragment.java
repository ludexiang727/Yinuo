package com.yinuo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.mapapi.map.MapView;
import com.yinuo.R;
import com.yinuo.helper.MapHelper;
import com.yinuo.mode.AddressModel;

/**
 * Created by ludexiang on 2016/4/27.
 */
public class LocationFragment extends Fragment implements View.OnClickListener {
    private MapView mMapView;
    private Context mContext;
    private MapHelper mMapHelper;
    private ImageView mNavigationStyle;
    private MapHelper.NavigationStyle mStyle = MapHelper.NavigationStyle.NORMAL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_location_fragment_layout, null);
        mMapView = (MapView) view.findViewById(R.id.app_location_view_map);
        mNavigationStyle = (ImageView) view.findViewById(R.id.app_location_style);
        mContext = getActivity();
        mMapHelper = new MapHelper(mMapView);
        mMapView.showZoomControls(false);
        mNavigationStyle.setOnClickListener(this);
        return view;
    }

//    public String getLocationPosition() {
//
//    }

    /** start location -- 开始定位 */
    public void startLocation() {
        mMapHelper.locationEnable(true);
        mMapHelper.location();
    }

    public void startLocationWithRoute(AddressModel from, AddressModel to) {
        mMapHelper.startLocationWithRoute(from, to);
    }

    public void addRoute(MapHelper.RouteWay way, AddressModel from, AddressModel to) {
        mMapHelper.searchRoute(way, from, to);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_location_style: {
                if (mStyle == MapHelper.NavigationStyle.NORMAL) {
                    mStyle = MapHelper.NavigationStyle.FOLLOWING;
                    mMapHelper.setNavigationStyle(MapHelper.NavigationStyle.FOLLOWING);
                } else if (mStyle == MapHelper.NavigationStyle.FOLLOWING) {
                    mStyle = MapHelper.NavigationStyle.COMPASS;
                    mMapHelper.setNavigationStyle(MapHelper.NavigationStyle.COMPASS);
                } else if (mStyle == MapHelper.NavigationStyle.COMPASS) {
                    mStyle = MapHelper.NavigationStyle.NORMAL;
                    mMapHelper.setNavigationStyle(MapHelper.NavigationStyle.NORMAL);
                }
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapHelper.locationEnable(false);
        mMapView.onDestroy();
        mMapView = null;
    }
}
