package com.yinuo.adapter.base;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yinuo.R;
import com.yinuo.base.BaseApplication;
import com.yinuo.listener.IOnItemClickListener;
import com.yinuo.helper.ImageLoaderHelper;
import com.yinuo.mode.HomePageBannersModel;
import com.yinuo.ui.component.widget.common.FlipperViewGroup;
import com.yinuo.ui.component.widget.common.IndicatorView;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements FlipperViewGroup.IClickListener {
    protected final int TYPE_HEADER = 0;
    protected final int TYPE_NORMAL = 1;
    protected View mHeaderView;
    protected List<HomePageBannersModel> mBanners;

    protected abstract <E extends RecyclerViewHolder> void bindView(E holder, int position);

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

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
                if (loadedImage != null && imageView != null) {
                    imageView.setImageBitmap(loadedImage);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    public void setBanners(List<HomePageBannersModel> banners) {
        mBanners = banners;
        if (mBanners != null && mBanners.size() > 0) {
            mHeaderView = View.inflate(BaseApplication.getInstance().getContext(), R.layout.listview_headerview_layout, null);
            FlipperViewGroup flipperViewGroup = (FlipperViewGroup) mHeaderView.findViewById(R.id.app_header_listview_viewgroup);
            IndicatorView indicatorView = (IndicatorView) mHeaderView.findViewById(R.id.app_header_list_indicator);
            flipperViewGroup.setClickListener(this);
            flipperViewGroup.setIndicator(indicatorView);
            flipperViewGroup.setFlipperView(banners);
        }
    }

    /** 添加header之后要重新计算position*/
    public int getRealPosition(RecyclerViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public void onClick(int position) {

    }
}
