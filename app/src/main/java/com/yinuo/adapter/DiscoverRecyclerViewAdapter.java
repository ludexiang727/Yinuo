package com.yinuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.adapter.base.RecyclerViewHolder;
import com.yinuo.base.BaseObject;
import com.yinuo.mode.DiscoveryRecycleModel;
import com.yinuo.utils.ResUtils;

import java.util.List;

/**
 * Created by gus on 16/4/16.
 */
public class DiscoverRecyclerViewAdapter<T extends BaseObject> extends BaseRecyclerAdapter {
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
        private View rootView;

        public DiscoverViewHolder(View view) {
            super(view);
            rootView = view;

            propertyView = (TextView) view.findViewById(R.id.discover_holder_property);
            imageView = (ImageView) view.findViewById(R.id.discover_holder_img);
            titleView = (TextView) view.findViewById(R.id.discover_holder_title);
            summaryView = (TextView) view.findViewById(R.id.discover_holder_summary);
        }

        @Override
        public void onClick(View view) {
            if (iClickListener != null && mListHolder != null && mListHolder.size() > 0) {
                iClickListener.onItemClick(view, mListHolder.get(getLayoutPosition()), getLayoutPosition());
            }
        }
    }

    @Override
    protected <E extends RecyclerViewHolder> void bindView(E viewHolder, int position) {
        final DiscoverViewHolder holder = (DiscoverViewHolder) viewHolder;
        if (position < mListHolder.size() && mListHolder.get(position) instanceof DiscoveryRecycleModel) {
            DiscoveryRecycleModel model = (DiscoveryRecycleModel) mListHolder.get(position);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            if (position == 0 || position == 1) {
                int topMargin = ResUtils.getDimen(mContext, R.dimen.discover_navigation_parent_height);
                params.topMargin = topMargin;
            } else {
                params.topMargin = 0;
            }
            holder.rootView.setLayoutParams(params);
            holder.titleView.setText(model.getTitle());
            holder.propertyView.setText(model.getProperty());
            loadImage(model.getBannerOrImgURL(), holder.imageView);
            holder.summaryView.setText(model.getSummary());
        }
    }
}
