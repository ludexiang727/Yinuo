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
import com.yinuo.ui.component.widget.view.PartnerRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class PartnerPageFragment extends BaseFragment {
    private int mPageIndex;
    private final int PAGE_COUNT = 10;
    private int mPartnerCount;
    private List<PartnerRecyclerModel> mModels = new ArrayList<PartnerRecyclerModel>();
    private PartnerRecyclerView mPartnerRecyclerView;
    private UIHandler mHandler = new UIHandler();

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_partner_page_layout;
    }

    @Override
    public void initialize(View view) {
        mLoading = (Loading) view.findViewById(R.id.partner_page_loading);
        mPartnerRecyclerView = (PartnerRecyclerView) view.findViewById(R.id.partner_page_recycler_view);
        mPartnerRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);

        mPartnerRecyclerView.bindDatas(mModels);
    }

    @Override
    public void loadData() {
        mLoading.loading();
        NetRequest.getInstance().requestPartnerPageData(mPageIndex, PAGE_COUNT, 0, 0, this);
    }

    @Override
    public void onRefresh() {

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
}
