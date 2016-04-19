package com.yinuo.adapter.base;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yinuo.listener.IOnItemClickListener;
import com.yinuo.helper.ImageLoaderHelper;

/**
 * Created by ludexiang on 2016/4/18.
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    protected abstract <E extends RecyclerViewHolder> void bindView(E holder, int position);

    protected IOnItemClickListener iClickListener;

    public void setOnItemClickListener(IOnItemClickListener listener) {
        iClickListener = listener;
    }

    public void loadImage(String url, final ImageView imageView) {
        ImageLoaderHelper.getInstance().loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadedImage != null) {
                    imageView.setImageBitmap(loadedImage);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}
