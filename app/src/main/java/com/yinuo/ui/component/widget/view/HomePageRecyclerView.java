package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.yinuo.adapter.HomePageRecyclerViewAdapter;
import com.yinuo.base.BaseObject;
import com.yinuo.listener.IDynamicLoadListener;
import com.yinuo.listener.ITransationSceneListener;
import com.yinuo.mode.HomePageBannersModel;
import com.yinuo.ui.component.baseview.BaseRecyclerView;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class HomePageRecyclerView extends BaseRecyclerView {
    private HomePageRecyclerViewAdapter mPageAdapter;
    private Context mContext;

    private IDynamicLoadListener mDynamicLoadListener;
    private ITransationSceneListener mSceneListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public HomePageRecyclerView(Context context) {
        this(context, null);
    }

    public HomePageRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mPageAdapter = new HomePageRecyclerViewAdapter(mContext);

//        addHeaderView(headerView);
//        setOnItemClickListener(this);
//        setOnScrollListener(this);
        setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public <T extends BaseObject> void bindRecycleView(List<T> lists) {
        mPageAdapter.setCards(lists);
        setAdapter(mPageAdapter);
    }

    public HomePageRecyclerViewAdapter getPageAdapter() {
        return mPageAdapter;
    }

    public void setListener(IDynamicLoadListener loadListener, ITransationSceneListener sceneListener) {
        mDynamicLoadListener = loadListener;
        mSceneListener = sceneListener;
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Log.e("ldx", "position ..........." + position);
//        if (mSceneListener != null) {
//            mSceneListener.onTransation(position);
//        }
//    }

    public void setBanners(List<HomePageBannersModel> banners) {
        mPageAdapter.setBanners(banners);
    }

    @Override
    public HomePageRecyclerViewAdapter getRecyclerAdapter() {
        return mPageAdapter;
    }

}
