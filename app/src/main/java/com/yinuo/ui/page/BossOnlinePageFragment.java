package com.yinuo.ui.page;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.base.BaseObject;
import com.yinuo.mode.BossOnlineDataModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetBossOnlinePageObj;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.component.widget.view.BossOnlineRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 * 伯乐
 */
public class BossOnlinePageFragment extends BaseFragment {

    private int mPageIndex = 1;
    private final int PAGE_COUNT = 10;
    private BossOnlineRecyclerView mRecyclerView;
    private UIHandler mHandler = new UIHandler();
    private List<BossOnlineDataModel> mModels = new ArrayList<BossOnlineDataModel>();

    public static BossOnlinePageFragment newInstance(int index) {
        BossOnlinePageFragment fragment = new BossOnlinePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        isPrepared = true;
        loadData();
        return view;
    }

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_boss_online_page_layout;
    }

    @Override
    public void initialize(View view) {
        mLoading = (Loading) view.findViewById(R.id.boss_online_page_loading);
        mRecyclerView = (BossOnlineRecyclerView) view.findViewById(R.id.boss_online_page_recycler_view);

        mRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mRecyclerView.bindRecycleView(mModels);
    }

    @Override
    public void loadData() {
        if (!isPrepared || mHasLoadedOnce) {
            return;
        }
        mLoading.loading();
        NetRequest.getInstance().requestBossOnlinePageData(mPageIndex, PAGE_COUNT, this);
    }

    @Override
    public void onRefresh() {
        mHasLoadedOnce = false;
        mPageIndex = 1;
        loadData();
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetBossOnlinePageObj) {
            mHasLoadedOnce = true;
            NetBossOnlinePageObj boss = (NetBossOnlinePageObj) object;
            Message msg = mHandler.obtainMessage();
            msg.what = mHandler.NOTIFY_SUCCESS;
            msg.obj = boss;
            msg.sendToTarget();
        }
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
    }

    @Override
    public void onItemClick(BaseObject baseObject, int position) {
        super.onItemClick(baseObject, position);
    }

    private final class UIHandler extends Handler {
        private final int NOTIFY_SUCCESS = 0x000;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoading.dismiss();
            switch (msg.what) {
                case NOTIFY_SUCCESS: {
                    mSwipeRefreshLayout.setRefreshing(false);
                    NetBossOnlinePageObj obj = (NetBossOnlinePageObj) msg.obj;
                    if (obj != null) {
                        List<BossOnlineDataModel> models = obj.getModels();
                        if (mPageIndex == 1) {
                            mModels.clear();
                        }
                        if (models != null) {
                            mModels.addAll(models);
                            mRecyclerView.getRecyclerAdapter().notifyDataSetChanged();
                        }
                    }
                    break;
                }
            }
        }
    }
}
