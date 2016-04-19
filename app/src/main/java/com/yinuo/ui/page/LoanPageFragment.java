package com.yinuo.ui.page;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.mode.LoanGridViewModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetLoanPageObj;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.component.widget.view.LoanGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class LoanPageFragment extends BaseFragment {
    private LoanGridView mGridView;
    private List<LoanGridViewModel> mLoanOptions = new ArrayList<LoanGridViewModel>();
    private int mOptionId;
    private int mOptionsLocation;

    private UIHandler mHandler = new UIHandler();

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_loan_page_layout;
    }

    @Override
    public void initialize(View view) {
        mLoading = (Loading) view.findViewById(R.id.loan_page_loading);
        mGridView = (LoanGridView) view.findViewById(R.id.loan_page_grid_view);
        mGridView.setOptions(mLoanOptions);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void loadData() {
        mLoading.loading();
        NetRequest.getInstance().requestLoanPageData(0, 0, this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetLoanPageObj) {
            NetLoanPageObj loanPageObj = (NetLoanPageObj) object;
            Message msg = mHandler.obtainMessage();
            msg.obj = loanPageObj;
            msg.what = mHandler.NOTIFY_SUCCESS;
            msg.sendToTarget();
        }
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    private class UIHandler extends Handler {
        private final int NOTIFY_SUCCESS = 0x000;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoading.dismiss();
            switch (msg.what) {
                case NOTIFY_SUCCESS: {
                    NetLoanPageObj obj = (NetLoanPageObj) msg.obj;
                    if (obj != null && obj.getOptions() != null) {
                        mLoanOptions.addAll(obj.getOptions());
                        mGridView.getLoanAdapter().notifyDataSetChanged();
                    }
                    break;
                }
            }
        }
    }
}
