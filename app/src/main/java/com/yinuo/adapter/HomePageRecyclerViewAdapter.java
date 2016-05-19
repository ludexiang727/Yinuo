package com.yinuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.adapter.base.RecyclerViewHolder;
import com.yinuo.base.BaseObject;
import com.yinuo.mode.HomePageDataModel;
import com.yinuo.ui.component.widget.view.HomePageTagTextView;
import com.yinuo.utils.ResUtils;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 *
 * extends NetBaseObject so far support getItemType 1 after getItemType is 2 or more
 */
public class HomePageRecyclerViewAdapter<T extends BaseObject> extends BaseRecyclerAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<T> mBindData = null;

    public HomePageRecyclerViewAdapter(Context context) {
        super();
        mContext = context;

        mInflater = LayoutInflater.from(mContext);
    }

    public void setCards(List<T> t) {
        if (t != null) {
            mBindData = t;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        int pos = getRealPosition(holder);
        bindView(holder, pos);
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null
                ? mBindData != null ? mBindData.size() : 0
                : mBindData != null ? mBindData.size() + 1 : 1;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new HomeViewHolder(mHeaderView);
        }
        View view = mInflater.inflate(R.layout.home_page_listview_sub_layout, null);
        HomeViewHolder holder = new HomeViewHolder(view);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public <E extends RecyclerViewHolder> void bindView(E parentHolder, int position) {
        if (mBindData != null && position < mBindData.size() && mBindData.get(position) instanceof HomePageDataModel) {
            final HomeViewHolder holder = (HomeViewHolder) parentHolder;
            HomePageDataModel bind = (HomePageDataModel) mBindData.get(position);
            loadImage(bind.getImgURL(), holder.cardImg);
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

    private final class HomeViewHolder extends RecyclerViewHolder implements View.OnClickListener {
        private ImageView cardImg;
        private ImageView cardCollection;
        private TextView cardAttention;
        private TextView cardTitle;
        private LinearLayout cardTagsLayout;
        private TextView cardSummary;

        public HomeViewHolder(View view) {
            super(view);
            if (view == mHeaderView) {
                return;
            }

            cardImg = (ImageView) view.findViewById(R.id.home_page_card_img);
            cardCollection = (ImageView) view.findViewById(R.id.home_page_card_detail_option_collection);
            cardAttention = (TextView) view.findViewById(R.id.home_page_card_detail_option_attention);
            cardTitle = (TextView) view.findViewById(R.id.home_page_card_detail_title);
            cardTagsLayout = (LinearLayout) view.findViewById(R.id.home_page_card_detail_tags_layout);
            cardSummary = (TextView) view.findViewById(R.id.home_page_card_detail_summary);
        }

        @Override
        public void onClick(View v) {
            if (iClickListener != null && mBindData != null && getLayoutPosition() < mBindData.size()) {
                iClickListener.onItemClick(v, mBindData.get(getLayoutPosition()), getLayoutPosition());
            }
        }
    }
}
