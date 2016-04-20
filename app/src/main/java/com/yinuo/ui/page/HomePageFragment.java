package com.yinuo.ui.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.base.BaseObject;
import com.yinuo.mode.HomePageBannersModel;
import com.yinuo.mode.HomePageDataModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetHomePageObj;
import com.yinuo.ui.component.widget.view.HomePageRecyclerView;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.sub.APPDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class HomePageFragment extends BaseFragment {

    private HomePageRecyclerView mListView;
    private int mPageIndex = 1;
    private static final int PAGE_COUNT = 10;
    private int mDataCount;
    private List<HomePageDataModel> mCardLists = new ArrayList<HomePageDataModel>();
    private List<HomePageBannersModel> mBanners = new ArrayList<HomePageBannersModel>();
    private UIHandler mHandler = new UIHandler();

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_home_page_layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void initialize(View view) {
        mLoading = (Loading) view.findViewById(R.id.home_page_loading);
        mListView = (HomePageRecyclerView) view.findViewById(android.R.id.list);
        mListView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mListView.bindRecycleView(mCardLists);
        mListView.setListener(this, this);
        mListView.getRecyclerAdapter().setOnItemClickListener(this);
    }

    @Override
    public void loadData() {
        mLoading.loading();
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
            List<HomePageDataModel> cards = obj.getModeLists();
            if (mCardLists != null && mCardLists.size() > 0 && mPageIndex == 1) {
                mCardLists.clear();
                mHandler.sendEmptyMessage(UIHandler.NOTIFY_REFRESH_FINISHED);
            }
            if (cards != null) {
                mCardLists.addAll(cards);
            }
            if (/*mBanners.size() == 0 && */obj.getHomePageBanners() != null) {
                mBanners.clear();
                mBanners.addAll(obj.getHomePageBanners());
                mHandler.sendEmptyMessage(UIHandler.NOTIFY_HEADER_BANNERS);
            }

            mHandler.sendEmptyMessage(UIHandler.NOTIFY_DATA_CHANGED);
        }
    }

    private final class UIHandler extends Handler {
        private static final int NOTIFY_DATA_CHANGED = 0x000;
        private static final int NOTIFY_HEADER_BANNERS = 0x001;
        private static final int NOTIFY_REFRESH_FINISHED = 0x002;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NOTIFY_DATA_CHANGED: {
                    mLoading.dismiss();
                    mListView.getPageAdapter().notifyDataSetChanged();
                    break;
                }
                case NOTIFY_HEADER_BANNERS: {
                    mListView.setBanners(mBanners);
                    break;
                }
                case NOTIFY_REFRESH_FINISHED: {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        Log.e("ldx", "onRefresh...........");
        mPageIndex = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        Log.e("ldx", "onLoadMore...........");
    }


    @Override
    public void onItemClick(BaseObject baseObject, int position) {
        if (baseObject instanceof HomePageDataModel) {
            HomePageDataModel mode = (HomePageDataModel) baseObject;
            Intent details = new Intent(getActivity(), APPDetailsActivity.class);
            getActivity().startActivity(details);
        } else if (baseObject instanceof HomePageBannersModel) {
            HomePageBannersModel mode = (HomePageBannersModel) baseObject;
        }
    }

}
