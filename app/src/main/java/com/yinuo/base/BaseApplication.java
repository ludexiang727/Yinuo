package com.yinuo.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.yinuo.Constants;
import com.yinuo.helper.MapHelper;
import com.yinuo.listener.ILocation;
import com.yinuo.mode.AddressModel;
import com.yinuo.net.IRequestListener;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.utils.AssetUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class BaseApplication extends Application {

    private Context mContext;
    private static BaseApplication sInstance;
    private BDLocation mBDLocation;


    public static BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        mContext = getBaseContext();
        sInstance = this;


        loadAppConfig();
        loadAddress();
    }

    /** load app config **/
    private void loadAppConfig() {
        NetRequest.getInstance().requestConfig(new IRequestListener() {
            @Override
            public void onSuccess(NetBaseObject object) {
            }

            @Override
            public void onFail(NetBaseObject object) {

            }
        });
    }

    public Context getContext() {
        return mContext;
    }

    public void setBDLocation(BDLocation location) {
        mBDLocation = location;
    }

    public BDLocation getBDLocation() {
        return mBDLocation;
    }

    private void loadAddress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String province = AssetUtils.readFile("province.txt");
                AssetUtils.parseProvince(mContext, province);

                String city = AssetUtils.readFile("city_lists.txt");
                AssetUtils.parseCity(mContext, city);

                String cityArea =AssetUtils.readFile("city_area.txt");
                AssetUtils.parseCityArea(mContext, cityArea);
            }
        }).start();
    }
}
