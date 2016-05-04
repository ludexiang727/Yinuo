package com.yinuo.ui.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yinuo.Constants;
import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.base.BaseObject;
import com.yinuo.mode.InvestPageDataModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetInvestPageObj;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.component.widget.view.InvestRecyclerView;
import com.yinuo.ui.sub.invest.InvestWeChatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class InvestPageFragment extends BaseFragment {
    private int mPageIndex = 1;
    private final int PAGE_COUNT = 10;
    private InvestRecyclerView mInvestRecyclerView;
    private int mCount;

    private UIHandler mHandler = new UIHandler();
    private List<InvestPageDataModel> mModels = new ArrayList<InvestPageDataModel>();

    public static InvestPageFragment newInstance(int index) {
        InvestPageFragment fragment = new InvestPageFragment();
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
        return R.layout.fragment_invest_page_layout;
    }

    @Override
    public void initialize(View view) {
        mInvestRecyclerView = (InvestRecyclerView) view.findViewById(R.id.invest_page_recycler_view);
        mLoading = (Loading) view.findViewById(R.id.invest_page_loading);
        mInvestRecyclerView.isTopAddMargin(false);
        mInvestRecyclerView.bindRecycleView(mModels);
        mInvestRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mInvestRecyclerView.getRecyclerAdapter().setOnItemClickListener(this);
    }

    @Override
    public void loadData() {
        if (!isPrepared || mHasLoadedOnce) {
            return;
        }
        mLoading.loading();
        NetRequest.getInstance().requestInvestPageData(mPageIndex, PAGE_COUNT, this);
    }

    @Override
    public void onRefresh() {
        mHasLoadedOnce = false;
        mPageIndex = 1;
        loadData();
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetInvestPageObj) {
            mHasLoadedOnce = true;
            NetInvestPageObj obj = (NetInvestPageObj) object;
            Message msg = mHandler.obtainMessage();
            msg.what = mHandler.NOTIFY_SUCCESS;
            msg.obj = obj;
            msg.sendToTarget();
        }
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onItemClick(View v, BaseObject baseObject, int position) {
        super.onItemClick(v, baseObject, position);
        switch (v.getId()) {
            case R.id.invest_view_holder_chat: {
                Intent intent = new Intent(getActivity(), InvestWeChatActivity.class);
                intent.putExtra(Constants.INVEST_WECHAT_BOSS_ID, mModels.get(position).getInvestId());
                getActivity().startActivity(intent);
                break;
            }
        }
    }

    private class UIHandler extends Handler {
        private final int NOTIFY_SUCCESS = 0x000;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoading.dismiss();
            mSwipeRefreshLayout.setRefreshing(false);
            switch (msg.what) {
                case NOTIFY_SUCCESS: {
                    NetInvestPageObj obj = (NetInvestPageObj) msg.obj;
                    if (obj != null) {
                        if (mPageIndex == 1) {
                            mCount = obj.getCount();
                            mModels.clear();
                        }
                        List<InvestPageDataModel> lists = obj.getModels();
                        if (lists != null) {
                            mModels.addAll(lists);
                            mInvestRecyclerView.getRecyclerAdapter().notifyDataSetChanged();
                        }
                    }
                    break;
                }
            }
        }
    }
}
