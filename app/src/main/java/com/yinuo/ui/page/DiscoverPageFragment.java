package com.yinuo.ui.page;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.base.BaseObject;
import com.yinuo.listener.IOnItemClickListener;
import com.yinuo.mode.DiscoveryRecycleModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetDiscoveryPageObj;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.component.widget.view.DiscoverNavView;
import com.yinuo.ui.component.widget.view.DiscoverRecyclerView;
import com.yinuo.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class DiscoverPageFragment extends BaseFragment implements IOnItemClickListener {
    private int mPropertyId;

    private DiscoverRecyclerView mRecycleView;
    private LinearLayout mNavScrollViewParent;
    private UIHandler mHandler = new UIHandler();
    private List<DiscoveryRecycleModel> mRecycleLists = new ArrayList<DiscoveryRecycleModel>();
    private String[] mNavScrollViews;
    private int mNavDefaultChoose;

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_discover_page_layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void initialize(View view) {
        mLoading = (Loading) view.findViewById(R.id.discover_page_loading);
        mRecycleView = (DiscoverRecyclerView) view.findViewById(R.id.discover_recycle_view);
        mNavScrollViewParent = (LinearLayout) view.findViewById(R.id.discover_navigation_parent_layout);
        mRecycleView.setRecycleDatas(mRecycleLists);
        mRecycleView.getRecyclerAdapter().setIOnClickListener(this);
    }

    @Override
    public void loadData() {
        mLoading.loading();
        NetRequest.getInstance().requestDiscoveryPageData(mPropertyId, this);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetDiscoveryPageObj) {
            NetDiscoveryPageObj discovery = (NetDiscoveryPageObj) object;
            List<DiscoveryRecycleModel> models = discovery.getDiscoveryLists();
            if (models != null) {
                mRecycleLists.addAll(models);
            }
            mNavDefaultChoose = discovery.getDiscoveryNavDefault();
            mNavScrollViews = discovery.getDiscoveryNavScrollView();
            mHandler.sendEmptyMessage(mHandler.NOTIFY_SUCCESS);
        }
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    private class UIHandler extends Handler {
        private static final int NOTIFY_SUCCESS = 0x000;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoading.dismiss();
            switch (msg.what) {
                case NOTIFY_SUCCESS: {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (mNavScrollViews != null) {
                        for (int i = 0; i < mNavScrollViews.length; ++i) {
                            DiscoverNavView navView = new DiscoverNavView(DiscoverPageFragment.this.getContext());
                            navView.setNavText(mNavScrollViews[i], i == mNavDefaultChoose);
                            mNavScrollViewParent.addView(navView, i);
                        }
                    }
                    mRecycleView.getAdapter().notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onItemClick(BaseObject baseObject, int position) {

    }

}
