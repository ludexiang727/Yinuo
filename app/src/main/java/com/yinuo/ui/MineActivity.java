package com.yinuo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.base.BaseApplication;
import com.yinuo.mode.UserModel;

/**
 * Created by ludexiang on 2016/5/23.
 */
public class MineActivity extends BaseActivity {
    private UserModel mUserModel;

    private ImageView mHeaderView;
    private TextView mNickName;
    private TextView mAccount;
    private RelativeLayout mQrCode;
    private TextView mGender;
    private RelativeLayout mCreditReport;
    private TextView mLoanProcessing;
    private TextView mLoanRepayment;

    private TextView mRegisterPerResidence;
    private TextView mSchool;
    private TextView mHighestEdu;
    private TextView mEduMajor;
    private TextView mWorkIn;
    private TextView mJob;
    private TextView mIdCard;

    @Override
    protected int getContentLayout() {
        return R.layout.mine_page_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mUserModel = BaseApplication.getInstance().getUserModel();
        super.onCreate(savedInstanceState);
        showTitle(true);
        setMiddleTitle(R.string.mine_title);
    }

    @Override
    protected void loadView(View view) {
        mHeaderView = (ImageView) view.findViewById(R.id.mine_header);
        mNickName = (TextView) view.findViewById(R.id.mine_nick_name);
        mAccount = (TextView) view.findViewById(R.id.mine_account);
        mQrCode = (RelativeLayout) view.findViewById(R.id.mine_qrcode_layout);
        mGender = (TextView) view.findViewById(R.id.mine_gender);
        mCreditReport = (RelativeLayout) view.findViewById(R.id.mine_credit_report_layout);
        mLoanProcessing = (TextView) view.findViewById(R.id.mine_loan_process);
        mLoanRepayment = (TextView) view.findViewById(R.id.mine_loan_repayment);

        mRegisterPerResidence = (TextView) view.findViewById(R.id.mine_auchentication_location);
        mSchool = (TextView) view.findViewById(R.id.mine_auchentication_school);
        mHighestEdu = (TextView) view.findViewById(R.id.mine_auchentication_highest_education);
        mEduMajor = (TextView) view.findViewById(R.id.mine_auchentication_major);
        mWorkIn = (TextView) view.findViewById(R.id.mine_auchentication_work_in);
        mJob = (TextView) view.findViewById(R.id.mine_auchentication_job);
        mIdCard = (TextView) view.findViewById(R.id.mine_auchentication_id_card);
    }

    @Override
    protected void loadData() {
        if (mUserModel != null) {
            mNickName.setText(mUserModel.getUserNickName());
            mAccount.setText(mUserModel.getUserAccount());
            mGender.setText(mUserModel.getUserGender() == 1 ? "男" : "女");
            mRegisterPerResidence.setText(mUserModel.getUserRegisterLocation());
            mSchool.setText(mUserModel.getUserSchool());
            mHighestEdu.setText(mUserModel.getUserHighestEdu());
            mEduMajor.setText(mUserModel.getUserEduMajor());
            mWorkIn.setText(mUserModel.getUserWorkIn());
            mJob.setText(mUserModel.getUserJob());
            mIdCard.setText(mUserModel.getUserIdCard());
        }
        dismissLoading();
    }


}
