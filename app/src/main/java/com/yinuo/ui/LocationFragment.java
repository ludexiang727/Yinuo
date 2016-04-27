package com.yinuo.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.yinuo.R;
import com.yinuo.base.BaseApplication;
import com.yinuo.helper.LocationHelper;

/**
 * Created by ludexiang on 2016/4/27.
 */
public class LocationFragment extends Fragment implements View.OnClickListener {
    private MapView mMapView;
    private Context mContext;
    private LocationHelper mLocationHelper;
    private ImageView mNavigationStyle;
    private LocationHelper.NavigationStyle mStyle = LocationHelper.NavigationStyle.NORMAL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_location_fragment_layout, null);
        mMapView = (MapView) view.findViewById(R.id.app_location_view_map);
        mNavigationStyle = (ImageView) view.findViewById(R.id.app_location_style);
        mContext = getActivity();
        mLocationHelper = LocationHelper.getInstance(mContext, mMapView);
        mLocationHelper.locationEnable(true);
        mMapView.showZoomControls(false);
        mLocationHelper.location();
        mNavigationStyle.setOnClickListener(this);
        return view;
    }

//    public String getLocationPosition() {
//
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_location_style: {
                if (mStyle == LocationHelper.NavigationStyle.NORMAL) {
                    mStyle = LocationHelper.NavigationStyle.FOLLOWING;
                    mLocationHelper.setNavigationStyle(LocationHelper.NavigationStyle.FOLLOWING);
                } else if (mStyle == LocationHelper.NavigationStyle.FOLLOWING) {
                    mStyle = LocationHelper.NavigationStyle.COMPASS;
                    mLocationHelper.setNavigationStyle(LocationHelper.NavigationStyle.COMPASS);
                } else if (mStyle == LocationHelper.NavigationStyle.COMPASS) {
                    mStyle = LocationHelper.NavigationStyle.NORMAL;
                    mLocationHelper.setNavigationStyle(LocationHelper.NavigationStyle.NORMAL);
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
        mLocationHelper.locationEnable(false);
        mMapView.onDestroy();
        mMapView = null;
    }
}
