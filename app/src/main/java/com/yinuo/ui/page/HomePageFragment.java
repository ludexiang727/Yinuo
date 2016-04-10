package com.yinuo.ui.page;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.mode.HomePageDataMode;
import com.yinuo.net.IRequestListener;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetHomePageObj;
import com.yinuo.ui.component.widget.view.HomePageListView;
import com.yinuo.ui.component.widget.Loading;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class HomePageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HomePageListView mListView;
    private int mPageIndex = 1;
    private static final int PAGE_COUNT = 10;
    private int mDataCount;
    private List<HomePageDataMode> mCardLists = new ArrayList<HomePageDataMode>();
    private List<String> mBanners = new ArrayList<String>();
    private UIHandler mHandler = new UIHandler();
    private Loading mLoading;

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_home_page_layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        loadData();
        return view;
    }

    @Override
    public void initialize(View view) {
        Log.e("ldx", "initialize .........");
        mLoading = (Loading) view.findViewById(R.id.home_page_loading);
        mLoading.loading();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_page_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mListView = (HomePageListView) view.findViewById(android.R.id.list);
        mListView.setCardLists(mCardLists);
    }

    private void loadData() {
        NetRequest.getInstance().requestHomePageData(mPageIndex, PAGE_COUNT, this);
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetHomePageObj) {
            NetHomePageObj obj = (NetHomePageObj) object;
            mDataCount = obj.getDataTotalCount();
            List<HomePageDataMode> cards = obj.getModeLists();
            if (cards != null) {
                mCardLists.addAll(cards);
            }
            if (mBanners.size() == 0 && obj.getHomePageBanners() != null) {
                mBanners.addAll(obj.getHomePageBanners());
            }

            mHandler.sendEmptyMessage(UIHandler.NOTIFY_DATA_CHANGED);
        }
    }

    private final class UIHandler extends Handler {
        private static final int NOTIFY_DATA_CHANGED = 0x000;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NOTIFY_DATA_CHANGED: {
                    mLoading.dismiss();
                    mListView.getPageAdapter().notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Override
    public void onRefresh() {

    }
}
