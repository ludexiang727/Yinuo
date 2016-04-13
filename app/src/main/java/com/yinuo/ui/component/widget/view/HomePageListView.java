package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yinuo.R;
import com.yinuo.adapter.HomePageListViewAdapter;
import com.yinuo.listener.IDynamicLoadListener;
import com.yinuo.listener.ITransationSceneListener;
import com.yinuo.mode.HomePageBannersMode;
import com.yinuo.mode.HomePageDataMode;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class HomePageListView extends ListView implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    private HomePageListViewAdapter mPageAdapter;
    private Context mContext;
    private List<HomePageDataMode> mCardLists = null;
    private FlipperViewGroup mFlipperViewGroup;
    private IDynamicLoadListener mDynamicLoadListener;
    private ITransationSceneListener mSceneListener;

    public HomePageListView(Context context) {
        this(context, null);
    }

    public HomePageListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mPageAdapter = new HomePageListViewAdapter(mContext);
        View headerView = View.inflate(mContext, R.layout.home_page_listview_headerview_layout, null);
        mFlipperViewGroup = (FlipperViewGroup) headerView.findViewById(R.id.home_page_listview_viewgroup);
        addHeaderView(headerView);
        setOnItemClickListener(this);
        setOnScrollListener(this);
    }

    public void setCardLists(List<HomePageDataMode> cards) {
        if (cards != null) {
            mCardLists = cards;
        }

        mPageAdapter.setCards(mCardLists);
        setAdapter(mPageAdapter);
    }

    public HomePageListViewAdapter getPageAdapter() {
        return mPageAdapter;
    }

    public void setListener(IDynamicLoadListener loadListener, ITransationSceneListener sceneListener) {
        mDynamicLoadListener = loadListener;
        mSceneListener = sceneListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("ldx", "position ..........." + position);
        if (mSceneListener != null) {
            mSceneListener.onTransation(position);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem != 0 && firstVisibleItem + visibleItemCount == totalItemCount && mDynamicLoadListener != null) {
            mDynamicLoadListener.onLoadMore();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public void setBanners(List<HomePageBannersMode> banners) {
        mFlipperViewGroup.setFlipperView(banners);
    }


}
