package com.yinuo.helper;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by ludexiang on 2016/4/27.
 */
public class LocationHelper {

    private Context mContext;
    private BaiduMap mBaiduMap;
    private MapView mMapView;
    private LocationListener mLocationListener;
    private static LocationHelper sInstance;
    private boolean isFirstLocation;
    public static enum NavigationStyle {NORMAL, COMPASS, FOLLOWING}
    private MyLocationConfiguration.LocationMode mCurrentMode;

    private LocationHelper(Context context, MapView view) {
        mContext = context;
        mMapView = view;
        mBaiduMap = mMapView.getMap();
        mLocationListener = new LocationListener();
    }

    public static LocationHelper getInstance(Context context, MapView mapView) {
        return LocationFactory.createInstance(context, mapView);
    }

    private static final class LocationFactory {
        public static LocationHelper createInstance(Context context, MapView view) {
            if (sInstance == null) {
                sInstance = new LocationHelper(context, view);
            }
            return sInstance;
        }
    }

    /** start location enable */
    public void locationEnable(boolean enable) {
        mBaiduMap.setMyLocationEnabled(enable);
    }

    public void location() {
        if (mBaiduMap.isMyLocationEnabled()) {
            LocationClient client = new LocationClient(mContext);
            client.registerLocationListener(mLocationListener);
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true); // 打开gps
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setScanSpan(1000);
            client.setLocOption(option);
            client.start();
        }
    }

    public void setNavigationStyle(NavigationStyle style) {
        switch (style) {
            case NORMAL:{
                mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                break;
            }
            case FOLLOWING:{
                mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                break;
            }
            case COMPASS: {
                mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                break;
            }
        }
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, null));
    }

    public MyLocationData getLocalData() {
        return mBaiduMap.getLocationData();
    }

//    public String getLocationPosition() {
//        return mBaiduMap.getLocationData().
//    }

    private final class LocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            Log.e("ldx", "location ..........." + location + " mapview " + mMapView);
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLocation) {
                isFirstLocation = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }
}
