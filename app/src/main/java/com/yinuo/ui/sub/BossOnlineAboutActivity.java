package com.yinuo.ui.sub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.base.BaseFragmentActivity;
import com.yinuo.helper.UiHelper;
import com.yinuo.listener.ILocationView;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.ui.LocationView;
import com.yinuo.utils.AppUtils;

/**
 * Created by Administrator on 2016/4/26.
 */
public class BossOnlineAboutActivity extends BaseFragmentActivity implements View.OnClickListener, ILocationView {
    public static final String COMPANY_ID = "company_id";
    private Intent mIntent;
    private int mCompanyId;

    private LocationView mLocationView;
    private RelativeLayout mParent;
    private RelativeLayout mLocationAdrParent;
    private LinearLayout mLocationParent;
    private ScrollView mScrollView;
    private int mScreenHeight;

    @Override
    protected int getContentLayout() {
        return R.layout.boss_online_about_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mIntent = getIntent();
        mCompanyId = mIntent.getIntExtra(COMPANY_ID, 0);
        super.onCreate(savedInstanceState);

        int screen[] = AppUtils.obtainScreenWidthAndHeight(this);
        mScreenHeight = screen[1];
    }

    @Override
    public void initView(View view) {
    }

    @Override
    protected void loadView(View view) {
        mLocationView = (LocationView) view.findViewById(R.id.location_view);
        mParent = (RelativeLayout) view.findViewById(R.id.boss_online_about_view_parent);
        mLocationAdrParent = (RelativeLayout) view.findViewById(R.id.boss_online_about_company_adr_parent);
        mLocationParent = (LinearLayout) view.findViewById(R.id.boss_online_about_location_parent);
        mScrollView = (ScrollView) view.findViewById(R.id.boss_online_about_scroll_view);

        mLocationView.setSupportFragmentManager(getSupportFragmentManager());
        mLocationView.setLocationListener(this);
        mLocationParent.setOnClickListener(this);

    }

    @Override
    protected void loadData() {
        NetRequest.getInstance().requestBossOnlineAboutPageData(mCompanyId, this);
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.boss_online_about_location_parent: {
                showLocationView();
                break;
            }
        }
    }

    private void showLocationView() {
        int topWidth = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int topHeight = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        mLocationAdrParent.measure(topWidth, topHeight);
        int height = mLocationAdrParent.getMeasuredHeight();
        int scrollViewHeight = mScreenHeight - height;
        AnimatorSet set = new AnimatorSet();
        Animator adrParentView = UiHelper.translateY(mLocationAdrParent, 0, -height, 200L, false);
        Animator scrollView = UiHelper.translateY(mScrollView, 0, scrollViewHeight, 200L, false);
        Animator alpha = UiHelper.alpha(mParent, 1f, 0f, 300L, false);
        set.setDuration(500L);
        set.playTogether(adrParentView, scrollView, alpha);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mLocationView.setVisibility(View.VISIBLE);
                mLocationView.showOptions();
                UiHelper.scaleZoomOut(mLocationView, 0.5f, 1f, 200L, true);
                mParent.setVisibility(View.GONE);
            }
        });
        set.start();
    }

}
