package com.yinuo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yinuo.R;
import com.yinuo.mode.DiscoveryRecycleModel;
import com.yinuo.utils.ImageLoaderHelper;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by gus on 16/4/16.
 */
public class DiscoverRecyclerViewAdapter <T extends DiscoveryRecycleModel> extends RecyclerView.Adapter<DiscoverRecyclerViewAdapter.Holder> {
    public Context mContext;
    private LayoutInflater mInflater;
    private List<T> mListHolder;

    public DiscoverRecyclerViewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void loadData(List<T> models) {
        mListHolder = models;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder = new Holder(mInflater.inflate(R.layout.discover_page_recycler_sub_layout, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(final DiscoverRecyclerViewAdapter.Holder holder, int position) {
        DiscoveryRecycleModel model = mListHolder.get(position);
        holder.titleView.setText(model.getTitle());
        holder.propertyView.setText(model.getProperty());
        ImageLoaderHelper.getInstance().loadImage(model.getBannerURL(), holder.imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadedImage != null) {
                    holder.imageView.setImageBitmap(loadedImage);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        holder.summaryView.setText(model.getSummary());
    }

    @Override
    public int getItemCount() {
        return mListHolder != null ? mListHolder.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView propertyView;
        private TextView titleView;
        private TextView summaryView;

        public Holder(View view) {
            super(view);
            propertyView = (TextView) view.findViewById(R.id.discover_holder_property);
            imageView = (ImageView) view.findViewById(R.id.discover_holder_img);
            titleView = (TextView) view.findViewById(R.id.discover_holder_title);
            summaryView = (TextView) view.findViewById(R.id.discover_holder_summary);
        }
    }

}
