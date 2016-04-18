package com.yinuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.adapter.base.RecyclerViewHolder;
import com.yinuo.mode.DiscoveryRecycleModel;

import java.util.List;

/**
 * Created by gus on 16/4/16.
 */
public class DiscoverRecyclerViewAdapter <T extends DiscoveryRecycleModel> extends BaseRecyclerAdapter {
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
    public DiscoverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.discover_page_recycler_sub_layout, null);
        DiscoverViewHolder holder = new DiscoverViewHolder(view);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return mListHolder != null ? mListHolder.size() : 0;
    }

    private final class DiscoverViewHolder extends RecyclerViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView propertyView;
        private TextView titleView;
        private TextView summaryView;

        public DiscoverViewHolder(View view) {
            super(view);
            propertyView = (TextView) view.findViewById(R.id.discover_holder_property);
            imageView = (ImageView) view.findViewById(R.id.discover_holder_img);
            titleView = (TextView) view.findViewById(R.id.discover_holder_title);
            summaryView = (TextView) view.findViewById(R.id.discover_holder_summary);
        }

        @Override
        public void onClick(View view) {
            if (iClickListener != null && mListHolder != null && mListHolder.size() > 0) {
                iClickListener.onItemClick(mListHolder.get(getLayoutPosition()), getLayoutPosition());
            }
        }
    }

    @Override
    protected <E extends RecyclerViewHolder> void bindView(E viewHolder, int position) {
        final DiscoverViewHolder holder = (DiscoverViewHolder) viewHolder;
        DiscoveryRecycleModel model = mListHolder.get(position);
        holder.titleView.setText(model.getTitle());
        holder.propertyView.setText(model.getProperty());
        loadImage(model.getBannerOrImgURL(), holder.imageView);
        holder.summaryView.setText(model.getSummary());
    }
}
