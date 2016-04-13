package com.yinuo.ui.sub;

import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.mode.HomeItemDetailBannerMode;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetHomePageDetailsObj;
import com.yinuo.ui.component.widget.view.FlipperViewGroup;
import com.yinuo.ui.component.widget.view.FloatingOptionView;
import com.yinuo.ui.component.widget.view.IconRoundCornerProgressBar;
import com.yinuo.utils.AppUtils;
import com.yinuo.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class HomePageDetailsActivity extends BaseActivity implements View.OnClickListener {

    private final int UPDATE_UI = 0x000;
    private FlipperViewGroup mDetailsFlipperViewGroup;
    private int mAppID;
    private List<HomeItemDetailBannerMode> mItemBanners = new ArrayList<HomeItemDetailBannerMode>();
    private FloatingActionButton mFloatActionButton;
    private IconRoundCornerProgressBar mProgressBar;
    private Button mDownload;
    private TextView mDownloadPercent;
    private RelativeLayout mOptionsParent;
    private int mFabMargin;
    private int mScreenWidth;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_UI: {
                    mDetailsFlipperViewGroup.setFlipperView(mItemBanners);
                    break;
                }
            }
        }
    };

    @Override
    protected int getContentLayout() {
        return R.layout.home_page_details_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        mScreenWidth = AppUtils.obtainScreenWidthAndHeight(this)[0];
        mFabMargin = ResUtils.getInt(this, R.dimen.fab_margin);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadView(final View view) {
        mFloatActionButton = (FloatingActionButton) view.findViewById(R.id.home_page_detail_floating_action_button);
        mDetailsFlipperViewGroup = (FlipperViewGroup) view.findViewById(R.id.home_page_detail_flipper_viewgroup);
        mProgressBar = (IconRoundCornerProgressBar) view.findViewById(R.id.home_page_detail_progress);
        mOptionsParent = (RelativeLayout) view.findViewById(R.id.home_page_details_options_parent);
        mDownload = (Button) view.findViewById(R.id.home_page_detail_download);
        mDownloadPercent = (TextView) view.findViewById(R.id.home_page_detail_percent);
        mDownload.setOnClickListener(this);
        mFloatActionButton.setOnClickListener(this);
        AppUtils.viewOnLocation(mFloatActionButton);
    }

    @Override
    public void loadData() {
        NetRequest.getInstance().requestAppDetails(mAppID, this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_page_detail_download: {
                mProgressBar.setProgressBackgroundColor(R.color.custom_progress_background);
                test();
                break;
            }
            case R.id.home_page_detail_floating_action_button: {
                int[] location = AppUtils.viewOnLocation(mFloatActionButton);
                int floatWidth = mScreenWidth - location[0] - mFabMargin;
                int left = mScreenWidth - mFabMargin - floatWidth * 2;
                int top = location[1] - floatWidth;

                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mOptionsParent.getLayoutParams();
                params.leftMargin = left;
                params.topMargin = top - mFabMargin - 11;
                params.width = /*mScreenWidth - left*/ ViewGroup.LayoutParams.MATCH_PARENT;
                mOptionsParent.setLayoutParams(params);

                // two child join  invest
                addOptionChild(location, floatWidth, params);
                break;
            }
        }
    }

    private void addOptionChild(int[] location, int floatWidth, CoordinatorLayout.LayoutParams params) {
        if (mOptionsParent != null && mOptionsParent.getChildCount() > 0) {
            mOptionsParent.removeAllViews();
        }

        RelativeLayout.LayoutParams optionParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RectF inner = new RectF(110 + mFabMargin / 2, 110 + mFabMargin / 2, 353 - mFabMargin / 2, 352 - mFabMargin / 2);
        RectF outer = new RectF(15, 15, 452 - 15, mOptionsParent.getMeasuredHeight() - 15);
        // child one
        FloatingOptionView optionJoin = new FloatingOptionView(this);
        optionJoin.setInner(inner);
        optionJoin.setOuter(outer);
        optionJoin.setText(ResUtils.getString(this, R.string.home_page_detail_join));
        optionJoin.setLayoutParams(optionParams);
        optionJoin.setOptionDirection(FloatingOptionView.OptionDirection.LEFT);
//        optionJoin.setFloatActionLocation(location, floatWidth, mFabMargin);
        // child two
        FloatingOptionView optionInvest = new FloatingOptionView(this);
        optionInvest.setInner(inner);
        optionInvest.setOuter(outer);
        optionInvest.setText(ResUtils.getString(this, R.string.home_page_detail_invest));
        optionInvest.setLayoutParams(optionParams);
        optionInvest.setOptionDirection(FloatingOptionView.OptionDirection.BOTTOM);

        mOptionsParent.addView(optionJoin);
        mOptionsParent.addView(optionInvest);
    }

    /// test
    int progress = 0;
    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                while(flag) {
                    while (progress <= 100) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        progress++;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(progress);

                            }
                        });
                        if (progress >= 100) {
                            flag = false;
                        }
                    }

                }
            }
        }).start();
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetHomePageDetailsObj) {
            NetHomePageDetailsObj detailsObj = (NetHomePageDetailsObj) object;
            List<HomeItemDetailBannerMode> images = detailsObj.getDetailsImgs();
            if (images != null && !images.isEmpty()){
                mItemBanners.addAll(images);
            }
            mHandler.sendEmptyMessage(UPDATE_UI);
        }
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }


}
