package com.yinuo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.yinuo.Constants;
import com.yinuo.base.BaseApplication;

import java.io.File;

/**
 * Created by Administrator on 2016/4/9.
 */
public class ImageLoaderHelper {
    private static ImageLoaderHelper sLoaderHelper;
    private DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();

    private ImageLoader mImageLoader;
    private Context mContext;

    private ImageLoaderHelper() {
        mImageLoader = ImageLoader.getInstance();
        mContext = BaseApplication.getInstance().getApplicationContext();
        initImageLoaderConfig();
    }

    public static ImageLoaderHelper getInstance() {
        return LoadHelperFactory.create();
    }

    private void initImageLoaderConfig() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(mContext, Constants.EXTERNAL_PATH);  //缓存文件夹路径
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
                .threadPoolSize(3) // default  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default 可以自定义缓存路径
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(mContext, 5 * 1000, 30 * 1000))
                .writeDebugLogs() // 打印debug log
                .build(); //开始构建

        mImageLoader.init(config);
    }

    private static final class LoadHelperFactory {
        public static ImageLoaderHelper create() {
            if (sLoaderHelper == null) {
                sLoaderHelper = new ImageLoaderHelper();
            }
            return sLoaderHelper;
        }
    }

    public void loadImage(String uri, ImageLoadingListener listener) {
        loadImage(uri, mOptions, listener);
    }

    public void loadImage(String uri, ImageView imageView, ImageLoadingListener listener) {
        loadImage(uri, imageView, mOptions, listener);
    }

    private void loadImage(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener) {
        mImageLoader.displayImage(uri, imageView, options, listener);
    }

    private void loadImage(String uri, DisplayImageOptions options, ImageLoadingListener listener) {
        mImageLoader.loadImage(uri, options, listener);
    }
}
