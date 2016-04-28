package com.yinuo.ui.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yinuo.Constants;
import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.base.BaseObject;
import com.yinuo.listener.IOnItemClickListener;
import com.yinuo.mode.LoanGridViewModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetLoanPageObj;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.component.widget.view.LoanGridView;
import com.yinuo.ui.sub.LoanApplyActivity;
import com.yinuo.ui.sub.LoanBalanceActivity;
import com.yinuo.ui.sub.LoanCCRepayActivity;
import com.yinuo.ui.sub.LoanCalculatorActivity;
import com.yinuo.ui.sub.LoanCardTicketActivity;
import com.yinuo.ui.sub.LoanCreditCardActivity;
import com.yinuo.ui.sub.LoanCreditReportActivity;
import com.yinuo.ui.sub.LoanExtremityActivity;
import com.yinuo.ui.sub.LoanProgressActivity;
import com.yinuo.ui.sub.LoanRateQueryActivity;
import com.yinuo.ui.sub.LoanRecepitActivity;
import com.yinuo.ui.sub.LoanRepaymentActivity;
import com.yinuo.ui.sub.LoanStrategyActivity;
import com.yinuo.ui.sub.LoanTransAccActivity;
import com.yinuo.ui.sub.LoanVipUniqueActivity;

import java.util.ArrayList;
import java.util.List;

//import io.card.payment.CardIOActivity;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class LoanPageFragment extends BaseFragment implements IOnItemClickListener, View.OnClickListener {
    private LinearLayout mScanParent;
    private LinearLayout mRedbagParent;
    private LinearLayout mVipParent;
    private LoanGridView mGridView;
    private List<LoanGridViewModel> mLoanOptions = new ArrayList<LoanGridViewModel>();
    private int mOptionId;
    private int mOptionsLocation;

    private UIHandler mHandler = new UIHandler();

    public static LoanPageFragment newInstance(int index) {
        LoanPageFragment fragment = new LoanPageFragment();
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
        return R.layout.fragment_loan_page_layout;
    }

    @Override
    public void initialize(View view) {
        mScanParent = (LinearLayout) view.findViewById(R.id.loan_page_scan_layout);
        mRedbagParent = (LinearLayout) view.findViewById(R.id.loan_page_redbag_parent);
        mVipParent = (LinearLayout) view.findViewById(R.id.loan_page_vip_layout);
        mLoading = (Loading) view.findViewById(R.id.loan_page_loading);
        mGridView = (LoanGridView) view.findViewById(R.id.loan_page_grid_view);
        mGridView.setOptions(mLoanOptions, this);
        mSwipeRefreshLayout.setEnabled(false);

        mScanParent.setOnClickListener(this);
        mRedbagParent.setOnClickListener(this);
        mVipParent.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        if (!isPrepared || mHasLoadedOnce) {
            return;
        }
        mLoading.loading();
        NetRequest.getInstance().requestLoanPageData(0, 0, this);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetLoanPageObj) {
            mHasLoadedOnce = true;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loan_page_scan_layout: {
//                Intent scanIntent = new Intent(getContext(), CardIOActivity.class);
//
//                // customize these values to suit your needs.
//                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
//                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
//                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
//
//                // hides the manual entry button
//                // if set, developers should provide their own manual entry mechanism in the app
//                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default: false
//
//                // matches the theme of your application
//                scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, false); // default: false
//
//                // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
////                startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
//                startActivity(scanIntent);
                break;
            }
            case R.id.loan_page_redbag_parent: {
                break;
            }
            case R.id.loan_page_vip_layout: {
                Intent intent = new Intent(getContext(), LoanVipUniqueActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onItemClick(BaseObject baseObject, int position) {
        if (baseObject instanceof LoanGridViewModel) {
            LoanGridViewModel model = (LoanGridViewModel) baseObject;
            Intent intent = new Intent();
            switch (model.getItemId()) {
                case Constants.LOAN_PAGE_APPLY: {
                    intent.setClass(getContext(), LoanApplyActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_EXTREMITY: {
                    intent.setClass(getContext(), LoanExtremityActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_PROGRESS: {
                    intent.setClass(getContext(), LoanProgressActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_CREDIT_CARD: {
                    intent.setClass(getContext(), LoanCreditCardActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_CREDIT_CARD_REPAYMENT: {
                    intent.setClass(getContext(), LoanCCRepayActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_RECEPIT: {
                    intent.setClass(getContext(), LoanRecepitActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_TRANSFER_ACCOUNTS: {
                    intent.setClass(getContext(), LoanTransAccActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_BALANCE: {
                    intent.setClass(getContext(), LoanBalanceActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_CARD_TICKET: {
                    intent.setClass(getContext(), LoanCardTicketActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_CREDIT_REPORT: {
                    intent.setClass(getContext(), LoanCreditReportActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_CALCULATOR: {
                    intent.setClass(getContext(), LoanCalculatorActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_REPAYMENT: {
                    intent.setClass(getContext(), LoanRepaymentActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_STRATEGY: {
                    intent.setClass(getContext(), LoanStrategyActivity.class);
                    break;
                }
                case Constants.LOAN_PAGE_RATE_SEARCH: {
                    intent.setClass(getContext(), LoanRateQueryActivity.class);
                    break;
                }
            }
            startActivity(intent);
        }
    }
}
