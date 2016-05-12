package com.yinuo.helper;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.yinuo.R;
import com.yinuo.base.BaseApplication;
import com.yinuo.map.BikingRouteOverlay;
import com.yinuo.map.DrivingRouteOverlay;
import com.yinuo.map.OverlayManager;
import com.yinuo.map.TransitRouteOverlay;
import com.yinuo.map.WalkingRouteOverlay;
import com.yinuo.mode.AddressModel;
import com.yinuo.utils.StringUtils;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/27.
 */
public class MapHelper {

    private BaiduMap mBaiduMap;
    private MapView mMapView;
    /** location - 定位 begin */
    private LocationListener mLocationListener;
    private static MapHelper sInstance;
    private boolean isFirstLocation;
    public static enum NavigationStyle {NORMAL, COMPASS, FOLLOWING}
    private MyLocationConfiguration.LocationMode mCurrentMode;
    /** location - 定位 end */
    /** route line -- 路线规划 begin */
    public static enum RouteWay {DRIVING, BUS, WALK, BIKE};
    private RoutePlanSearch mRouteSearch;
    private RouteListener mRouteListener;
    private RouteLine mRoute;
    private OverlayManager mRouteOverlayManager;
    private RouteWay mWay = RouteWay.BUS;
    /** route line -- 路线规划 end */

    private MapHelper(MapView view) {
        mMapView = view;
        mBaiduMap = mMapView.getMap();
        mLocationListener = new LocationListener();
        mRouteListener = new RouteListener();
        mRouteSearch = RoutePlanSearch.newInstance();
        mRouteSearch.setOnGetRoutePlanResultListener(mRouteListener);
    }

    public void startLocationWithRoute(AddressModel from, AddressModel to) {
        PlanNode stNode = PlanNode.withCityNameAndPlaceName(from.getProvince(), from.getCityName());
        PlanNode enNode = PlanNode.withCityNameAndPlaceName(to.getProvince(), to.getCityName());
        mRouteSearch.transitSearch(new TransitRoutePlanOption().from(stNode).city(to.getProvince()).to(enNode));
    }

    public static MapHelper getInstance(MapView mapView) {
        return LocationFactory.createInstance(mapView);
    }

    private static final class LocationFactory {
        public static MapHelper createInstance(MapView view) {
            if (sInstance == null) {
                sInstance = new MapHelper(view);
            }
            return sInstance;
        }
    }

    /** start location enable */
    public void locationEnable(boolean enable) {
        mBaiduMap.setMyLocationEnabled(enable);
    }

    /** location -- 定位 */
    public void location() {
        LocationClient client = new LocationClient(BaseApplication.getInstance().getApplicationContext());
        client.registerLocationListener(mLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(2000);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);
        client.setLocOption(option);
        client.start();
//        client.requestLocation();
    }

    /** location position style -- 定位点样式 */
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

    /** location call back -- 定位回调接口 */
    private final class LocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.e("ldx", sb.toString());

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            final MapStatus.Builder builder = new MapStatus.Builder();
            if (!isFirstLocation) {
                isFirstLocation = true;
                builder.zoom(18f);
            }
            builder.target(ll);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()), 3000);
        }

    }

    /** route call back -- 路线规划回调接口 begin */
    public void searchRoute(RouteWay way, AddressModel startAddress, AddressModel endAddress) {
        if (startAddress == null || endAddress == null) {
            return;
        }
        if (StringUtils.isEmpty(startAddress.getAddress()) || StringUtils.isEmpty(endAddress.getAddress())) {
            return;
        }
        PlanNode stNode = PlanNode.withCityNameAndPlaceName(startAddress.getProvince(), startAddress.getCityName());
        PlanNode enNode = PlanNode.withCityNameAndPlaceName(endAddress.getProvince(), endAddress.getCityName());
        switch (way) {
            case DRIVING: {
                mRouteSearch.drivingSearch(new DrivingRoutePlanOption().from(stNode).to(enNode));
                break;
            }
            case BUS: {
                mRouteSearch.transitSearch(new TransitRoutePlanOption().from(stNode)
                        .city(endAddress.getProvince()).to(enNode));
                break;
            }
            case WALK: {
                mRouteSearch.walkingSearch(new WalkingRoutePlanOption().from(stNode).to(enNode));
                break;
            }
            case BIKE: {
                mRouteSearch.bikingSearch(new BikingRoutePlanOption().from(stNode).to(enNode));
                break;
            }
            default: {
                mRouteSearch.transitSearch(new TransitRoutePlanOption().from(stNode)
                        .city(endAddress.getProvince()).to(enNode));
                break;
            }
        }
    }

    private final class RouteListener implements OnGetRoutePlanResultListener {

        @Override
        public void onGetBikingRouteResult(BikingRouteResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                Toast.makeText(RoutePlanDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                // result.getSuggestAddrInfo()
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                mRoute = result.getRouteLines().get(0);
                BikingRouteOverlay overlay = new BikeRouteOverlay(mBaiduMap);
                mRouteOverlayManager = overlay;
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }

        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                Toast.makeText(RoutePlanDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                // result.getSuggestAddrInfo()
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                mRoute = result.getRouteLines().get(0);
                WalkingRouteOverlay overlay = new WalkRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                mRouteOverlayManager = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                Toast.makeText(RoutePlanDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                // result.getSuggestAddrInfo()
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                mRoute = result.getRouteLines().get(0);
                TransitRouteOverlay overlay = new TransitionRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                mRouteOverlayManager = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                Toast.makeText(RoutePlanDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                // result.getSuggestAddrInfo()
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                mRoute = result.getRouteLines().get(0);
                DrivingRouteOverlay overlay = new DriveRouteOverlay(mBaiduMap);
                mRouteOverlayManager = overlay;
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }
    }

    private final class WalkRouteOverlay extends WalkingRouteOverlay {
        public WalkRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (true) {
                return BitmapDescriptorFactory.fromResource(R.drawable.map_helper_start_address_icon);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (true) {
                return BitmapDescriptorFactory.fromResource(R.drawable.map_helper_end_address_icon);
            }
            return null;
        }
    }
    private final class TransitionRouteOverlay extends TransitRouteOverlay {
        public TransitionRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (true) {
                return BitmapDescriptorFactory.fromResource(R.drawable.map_helper_start_address_icon);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (true) {
                return BitmapDescriptorFactory.fromResource(R.drawable.map_helper_end_address_icon);
            }
            return null;
        }
    }
    private final class BikeRouteOverlay extends BikingRouteOverlay {
        public  BikeRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (true) {
                return BitmapDescriptorFactory.fromResource(R.drawable.map_helper_start_address_icon);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (true) {
                return BitmapDescriptorFactory.fromResource(R.drawable.map_helper_end_address_icon);
            }
            return null;
        }
    }
    private final class DriveRouteOverlay extends DrivingRouteOverlay {
        public DriveRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (true) {
                return BitmapDescriptorFactory.fromResource(R.drawable.map_helper_start_address_icon);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (true) {
                return BitmapDescriptorFactory.fromResource(R.drawable.map_helper_end_address_icon);
            }
            return null;
        }
    }
    /** route call back -- 路线规划回调接口 end */
}
