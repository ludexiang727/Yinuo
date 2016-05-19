package com.yinuo.ui.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.base.BaseObject;
import com.yinuo.mode.DiscoveryRecycleModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetDiscoveryPageObj;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.component.widget.view.DiscoverNavView;
import com.yinuo.ui.component.widget.view.DiscoverRecyclerView;
import com.yinuo.ui.sub.APPDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class DiscoverPageFragment extends BaseFragment {
    private int mPropertyId;
    private int mPageIndex = 1;
    private final int PAGE_COUNT = 10;
    private DiscoverRecyclerView mRecycleView;
    private LinearLayout mNavScrollViewParent;
    private UIHandler mHandler = new UIHandler();
    private List<DiscoveryRecycleModel> mRecycleLists = new ArrayList<DiscoveryRecycleModel>();
    private String[] mNavScrollViews;
    private int mNavDefaultChoose;

    public static DiscoverPageFragment newInstance(int index) {
        DiscoverPageFragment fragment = new DiscoverPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_discover_page_layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        isPrepared = true;
        loadData();
        return view;
    }

    @Override
    public void initialize(View view) {
        mLoading = (Loading) view.findViewById(R.id.discover_page_loading);
        mRecycleView = (DiscoverRecyclerView) view.findViewById(R.id.discover_recycle_view);
        mRecycleView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mNavScrollViewParent = (LinearLayout) view.findViewById(R.id.discover_navigation_parent_layout);
        postOptions();
        mRecycleView.bindRecycleView(mRecycleLists);
        mRecycleView.getRecyclerAdapter().setOnItemClickListener(this);
    }

    @Override
    public void loadData() {
        if (!isPrepared || mHasLoadedOnce) {
            return;
        }
        mLoading.loading();
        NetRequest.getInstance().requestDiscoveryPageData(mPropertyId, this);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetDiscoveryPageObj) {
            mHasLoadedOnce = true;
            NetDiscoveryPageObj discovery = (NetDiscoveryPageObj) object;
            Message msg = mHandler.obtainMessage();
            msg.what = mHandler.NOTIFY_SUCCESS;
            msg.obj = discovery;
            msg.sendToTarget();
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
                    NetDiscoveryPageObj discovery = (NetDiscoveryPageObj) msg.obj;
                    mNavDefaultChoose = discovery.getDiscoveryNavDefault();
                    mNavScrollViews = discovery.getDiscoveryNavScrollView();
                    postOptions();
                    List<DiscoveryRecycleModel> models = discovery.getDiscoveryLists();
                    if (mPageIndex == 1) {
                        mRecycleLists.clear();
                    }
                    if (models != null) {
                        mRecycleLists.addAll(models);
                    }
                    mRecycleView.getAdapter().notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    private void postOptions() {
        if (mNavScrollViews != null) {
            if (mNavScrollViewParent.getChildCount() > 0) {
                mNavScrollViewParent.removeAllViews();
            }
            for (int i = 0; i < mNavScrollViews.length; ++i) {
                DiscoverNavView navView = new DiscoverNavView(DiscoverPageFragment.this.getContext());
                navView.setNavText(mNavScrollViews[i], i == mNavDefaultChoose);
                mNavScrollViewParent.addView(navView, i);
            }
        }
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        mHasLoadedOnce = false;
        loadData();
    }

    @Override
    public void onItemClick(View v, BaseObject baseObject, int position) {
        if (baseObject instanceof DiscoveryRecycleModel) {
            DiscoveryRecycleModel discoveryModel = (DiscoveryRecycleModel) baseObject;
            Intent details = new Intent(getActivity(), APPDetailsActivity.class);
            getActivity().startActivity(details);
        }
    }

}
