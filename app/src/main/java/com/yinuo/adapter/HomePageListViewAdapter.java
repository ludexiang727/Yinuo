package com.yinuo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 *
 * extends NetBaseObject so far support getItemType 1 after getItemType is 2 or more
 */
public class HomePageListViewAdapter <T extends HomePageDataMode> extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<T> mBindData = null;

    public HomePageListViewAdapter (Context context) {
        mContext = context;

        mInflater = LayoutInflater.from(mContext);
    }

    public void setCards(List<T> t) {
        if (t != null) {
            mBindData = t;
        }
    }

    @Override
    public int getCount() {
        return mBindData != null ? mBindData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mBindData != null && mBindData.size() > 0 ? mBindData.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.home_page_listview_sub_layout, parent, false);

            holder.cardImg = (ImageView) convertView.findViewById(R.id.home_page_card_img);
            holder.cardCollection = (ImageView) convertView.findViewById(R.id.home_page_card_detail_option_collection);
            holder.cardAttention = (TextView) convertView.findViewById(R.id.home_page_card_detail_option_attention);
            holder.cardTitle = (TextView) convertView.findViewById(R.id.home_page_card_detail_title);
            holder.cardTagsLayout = (LinearLayout) convertView.findViewById(R.id.home_page_card_detail_tags_layout);
            holder.cardSummary = (TextView) convertView.findViewById(R.id.home_page_card_detail_summary);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        bindView(holder, position);
        return convertView;
    }

    private final class ViewHolder implements View.OnClickListener {
        private ImageView cardImg;
        private ImageView cardCollection;
        private TextView cardAttention;
        private TextView cardTitle;
        private LinearLayout cardTagsLayout;
        private TextView cardSummary;

        @Override
        public void onClick(View v) {

        }
    }

    private void bindView(final ViewHolder holder, int position) {
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
