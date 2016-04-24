package com.yinuo.ui.page;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.mode.InvestPageDataModel;
import com.yinuo.mode.PartnerRecyclerModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetInvestPageObj;
import com.yinuo.net.response.NetPartnerPageObj;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.component.widget.baseview.BaseRecyclerView;
import com.yinuo.ui.component.widget.view.InvestRecyclerView;
import com.yinuo.ui.component.widget.view.PartnerConditionView;
import com.yinuo.ui.component.widget.view.PartnerRecyclerView;
import com.yinuo.ui.component.widget.view.PartnerViewPager;
import com.yinuo.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 * 此页面包含 技术入伙（有想法但没有钱的人但是有技术） 和 其他 （有钱但是没有想法的人）
 */
public class PartnerPageFragment extends BaseFragment implements PartnerConditionView.IConditionListener, ViewPager.OnPageChangeListener {
    private int mPageIndex = 1;
    private final int PAGE_COUNT = 10;
    private int mPartnerCount;
    private int mInvestCount;
    private PartnerRecyclerView mPartnerRecyclerView;
    private InvestRecyclerView mInvestRecyclerView;
    private UIHandler mHandler = new UIHandler();
    private PartnerConditionView mSkill;
    private PartnerConditionView mInvest;
    private int mCondition;
    private int mCurrentPosition;
    private PartnerViewPager mViewPager;
    private List<BaseRecyclerView> mRecyclerViews = new ArrayList<BaseRecyclerView>();
    private List<PartnerRecyclerModel> mPartnerModels = new ArrayList<PartnerRecyclerModel>();
    private List<InvestPageDataModel> mInvestModels = new ArrayList<InvestPageDataModel>();

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_partner_page_layout;
    }

    @Override
    public void initialize(View view) {
        mSkill = (PartnerConditionView) view.findViewById(R.id.partner_page_condition_skill);
        mInvest = (PartnerConditionView) view.findViewById(R.id.partner_page_condition_invest);
        mLoading = (Loading) view.findViewById(R.id.partner_page_loading);
        mViewPager = (PartnerViewPager) view.findViewById(R.id.partner_view_pager);

        mSwipeRefreshLayout.setEnabled(false);

        mPartnerRecyclerView = new PartnerRecyclerView(getContext());
        mInvestRecyclerView = new InvestRecyclerView(getContext());

        mPartnerRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mPartnerRecyclerView.bindRecycleView(mPartnerModels);

        mInvestRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mInvestRecyclerView.bindRecycleView(mInvestModels);

        mSkill.setPosition(0);
        mInvest.setPosition(1);
        mSkill.setConditionListener(this);
        mInvest.setConditionListener(this);

        mRecyclerViews.add(mPartnerRecyclerView);
        mRecyclerViews.add(mInvestRecyclerView);

        mViewPager.setViews(mRecyclerViews);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void loadData() {
        mLoading.loading();
        if (mCondition == 0 && mViewPager.getCurrentItem() == 0) {
            NetRequest.getInstance().requestPartnerPageData(mPageIndex, PAGE_COUNT, 0, mCondition, this);
        } else if (mCondition == 1 && mViewPager.getCurrentItem() == 1) {
            NetRequest.getInstance().requestInvestPageData(mPageIndex, PAGE_COUNT, this);
        }
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

            Message msg = mHandler.obtainMessage(mHandler.PARTNER_NOTIFY_SUCCESS);
            msg.obj = obj;
            msg.sendToTarget();
        } else if (object instanceof NetInvestPageObj) {
            NetInvestPageObj obj = (NetInvestPageObj) object;

            Message msg = mHandler.obtainMessage();
            msg.what = mHandler.INVEST_NOTIFY_SUCCESS;
            msg.obj = obj;
            msg.sendToTarget();
        }

    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    private class UIHandler extends Handler {
        private final int PARTNER_NOTIFY_SUCCESS = 0x000;
        private final int INVEST_NOTIFY_SUCCESS = 0x0001;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoading.dismiss();
            if (mPageIndex == 1) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            switch (msg.what) {
                case PARTNER_NOTIFY_SUCCESS: {
                    NetPartnerPageObj obj = (NetPartnerPageObj) msg.obj;
                    if (obj != null) {
                        mPartnerCount = obj.getModelCount();
                        List<PartnerRecyclerModel> lists = obj.getModels();
                        if (lists != null) {
                            mPartnerModels.addAll(lists);
                            mPartnerRecyclerView.getRecyclerAdapter().notifyDataSetChanged();
                        }
                    }
                    break;
                }
                case INVEST_NOTIFY_SUCCESS: {
                    NetInvestPageObj obj = (NetInvestPageObj) msg.obj;
                    if (obj != null) {
                        mInvestCount = obj.getCount();
                        List<InvestPageDataModel> lists = obj.getModels();
                        if (lists != null) {
                            mInvestModels.addAll(lists);
                            mInvestRecyclerView.getRecyclerAdapter().notifyDataSetChanged();
                        }
                    }
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
            mCurrentPosition = 0;
            mViewPager.setCurrentItem(0);
        } else if (position == 1) {
            mSkill.setTextColor(normal);
            mInvest.setTextColor(selected);
            mCondition = 1;
            mViewPager.setCurrentItem(1);
            mCurrentPosition = 1;
        }
        // reload data
        loadData();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mCurrentPosition == position) {
            return;
        }

        onConditionClick(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
