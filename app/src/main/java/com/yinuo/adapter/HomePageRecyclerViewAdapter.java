package com.yinuo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yinuo.R;
import com.yinuo.mode.HomePageDataMode;
import com.yinuo.ui.component.widget.view.HomePageTagTextView;
import com.yinuo.utils.ImageLoaderHelper;
import com.yinuo.utils.ResUtils;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 *
 * extends NetBaseObject so far support getItemType 1 after getItemType is 2 or more
 */
public class HomePageRecyclerViewAdapter<T extends HomePageDataMode> extends RecyclerView.Adapter<HomePageRecyclerViewAdapter.HomeViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<T> mBindData = null;

    public HomePageRecyclerViewAdapter(Context context) {
        mContext = context;

        mInflater = LayoutInflater.from(mContext);
    }

    public void setCards(List<T> t) {
        if (t != null) {
            mBindData = t;
        }
    }

    @Override
    public void onBindViewHolder(HomePageRecyclerViewAdapter.HomeViewHolder holder, int position) {
        bindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return mBindData != null ? mBindData.size() : 0;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.home_page_listview_sub_layout, null);
        HomeViewHolder holder = new HomeViewHolder(view);
        return holder;
    }

    public final class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView cardImg;
        private ImageView cardCollection;
        private TextView cardAttention;
        private TextView cardTitle;
        private LinearLayout cardTagsLayout;
        private TextView cardSummary;

        public HomeViewHolder(View view) {
            super(view);

            cardImg = (ImageView) view.findViewById(R.id.home_page_card_img);
            cardCollection = (ImageView) view.findViewById(R.id.home_page_card_detail_option_collection);
            cardAttention = (TextView) view.findViewById(R.id.home_page_card_detail_option_attention);
            cardTitle = (TextView) view.findViewById(R.id.home_page_card_detail_title);
            cardTagsLayout = (LinearLayout) view.findViewById(R.id.home_page_card_detail_tags_layout);
            cardSummary = (TextView) view.findViewById(R.id.home_page_card_detail_summary);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private void bindView(final HomeViewHolder holder, int position) {
        if (mBindData != null && position < mBindData.size()) {
            HomePageDataMode bind = mBindData.get(position);
            ImageLoaderHelper.getInstance().loadImage(bind.getImgURL(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (loadedImage != null) {
                        holder.cardImg.setImageBitmap(loadedImage);
                    }
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
            holder.cardTitle.setText(bind.getTitle());
            holder.cardAttention.setText(String.format(ResUtils.getString(mContext, R.string.home_page_card_details_attention), bind.getAttention()));
            holder.cardSummary.setText(bind.getSummary());
            holder.cardCollection.setImageResource(bind.getCollectioned() == 1 ? 0 : 0);
            holder.cardCollection.setOnClickListener(holder);
            List<String> tags = bind.getTags();
            holder.cardTagsLayout.removeAllViews();
            for (String tag : tags) {
                HomePageTagTextView homePageTag = new HomePageTagTextView(mContext);
                homePageTag.setText(tag, HomePageTagTextView.TagBackGroundType.BLUE);
                holder.cardTagsLayout.addView(homePageTag);
            }
        }
    }
}
