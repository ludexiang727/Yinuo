package com.yinuo.ui.page;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.mode.PartnerRecyclerModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetPartnerPageObj;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.component.widget.view.PartnerConditionView;
import com.yinuo.ui.component.widget.view.PartnerRecyclerView;
import com.yinuo.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class PartnerPageFragment extends BaseFragment implements PartnerConditionView.IConditionListener {
    private int mPageIndex = 1;
    private final int PAGE_COUNT = 10;
    private int mPartnerCount;
    private List<PartnerRecyclerModel> mModels = new ArrayList<PartnerRecyclerModel>();
    private PartnerRecyclerView mPartnerRecyclerView;
    private UIHandler mHandler = new UIHandler();
    private PartnerConditionView mSkill;
    private PartnerConditionView mInvest;
    private int mCondition;

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_partner_page_layout;
    }

    @Override
    public void initialize(View view) {
        mSkill = (PartnerConditionView) view.findViewById(R.id.partner_page_condition_skill);
        mInvest = (PartnerConditionView) view.findViewById(R.id.partner_page_condition_invest);
        mLoading = (Loading) view.findViewById(R.id.partner_page_loading);
        mPartnerRecyclerView = (PartnerRecyclerView) view.findViewById(R.id.partner_page_recycler_view);
        mPartnerRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);

        mPartnerRecyclerView.bindRecycleView(mModels);
        mSkill.setPosition(0);
        mInvest.setPosition(1);

        mSkill.setConditionListener(this);
        mInvest.setConditionListener(this);
    }

    @Override
    public void loadData() {
        mLoading.loading();
        NetRequest.getInstance().requestPartnerPageData(mPageIndex, PAGE_COUNT, 0, mCondition, this);
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        loadData();
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetPartnerPageObj) {
            NetPartnerPageObj obj = (NetPartnerPageObj) object;

            Message msg = mHandler.obtainMessage(mHandler.NOTIFY_SUCCESS);
            msg.obj = obj;
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
                    if (mPageIndex == 1) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    NetPartnerPageObj obj = (NetPartnerPageObj) msg.obj;
                    mPartnerCount = obj.getModelCount();
                    List<PartnerRecyclerModel> lists = obj.getModels();
                    if (lists != null) {
                        mModels.addAll(lists);
                    }
                    mPartnerRecyclerView.getRecyclerAdapter().notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Override
    public void onConditionClick(int position) {
        int selected = ResUtils.getColor(getContext(), R.color.partner_page_condition_select_color);
        int normal = ResUtils.getColor(getContext(), R.color.partner_page_condition_normal_color);
        if (position == 0) {
            mSkill.setTextColor(selected);
            mInvest.setTextColor(normal);
            mCondition = 0;
        } else if (position == 1) {
            mSkill.setTextColor(normal);
            mInvest.setTextColor(selected);
            mCondition = 1;
        }
        // reload data
        loadData();
    }
}
