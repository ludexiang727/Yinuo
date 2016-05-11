package com.yinuo.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yinuo.base.BaseObject;
import com.yinuo.helper.ImageLoaderHelper;
import com.yinuo.listener.IOnItemClickListener;
import com.yinuo.mode.LoanGridViewModel;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/19.
 */
public abstract class SuperAdapter <T extends SuperViewHolder> extends BaseAdapter {
    protected IOnItemClickListener itemClickListener;
    protected abstract <E extends BaseObject> void bindView(SuperViewHolder superHolder, E base);
    protected abstract T getViewHolder();
    protected abstract View getView(int position);
    protected abstract void initHolder(SuperViewHolder superHolder, int position, View view);
    protected abstract <E extends BaseObject> List<E> getList();
    public abstract void setItemClickListener(IOnItemClickListener listener);

    protected LayoutInflater mInflater;

    public SuperAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SuperViewHolder holder;
        if (convertView == null) {
            holder = getViewHolder();
            convertView = getView(position);
            initHolder(holder, position, convertView);

            convertView.setTag(holder);
        } else {
            holder = (SuperViewHolder) convertView.getTag();
        }
        holder.setPosition(position);
        convertView.setOnClickListener(holder);

        if (getList() != null) {
            bindView(holder, getList().get(position));
        }
        return convertView;
    }

    /** child class base work handle bitmap */
    protected void loadBitmapSuccess(Bitmap bitmap, ImageView imageView) {
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

                    loadBitmapSuccess(loadedImage, imageView);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}
