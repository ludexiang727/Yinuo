package com.yinuo.ui.sub;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.mode.HomeItemDetailBannerModel;
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
public class APPDetailsActivity extends BaseActivity implements View.OnClickListener, FloatingOptionView.IClickListener {

    private int mAppID;
    private final int UPDATE_UI = 0x000;
    private FlipperViewGroup mDetailsFlipperViewGroup;
    private List<HomeItemDetailBannerModel> mItemBanners = new ArrayList<HomeItemDetailBannerModel>();
    private FloatingActionButton mFloatActionButton;
    private IconRoundCornerProgressBar mProgressBar;
    private Button mDownload;
    private TextView mDownloadPercent;
    private RelativeLayout mOptionsParent;
    private int mFabMargin;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mFloatingMarginTop;
    private int mOuterRectFMargin;

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
        mScreenHeight = AppUtils.obtainScreenWidthAndHeight(this)[1];
        mFabMargin = ResUtils.getInt(this, R.dimen.fab_margin);
        mFloatingMarginTop = ResUtils.getInt(this, R.dimen.home_page_detail_floating_top);
        mOuterRectFMargin = ResUtils.getInt(this, R.dimen.home_page_detail_outer_rect_margin);

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
                int[] location = AppUtils.viewOnLocationOnScreen(mFloatActionButton);
                int floatWidth = mScreenWidth - location[0] - mFabMargin;
                float centerX = location[0] + floatWidth / 2;
                float centerY = location[1] + 20;
                if (mOptionsParent != null && mOptionsParent.getVisibility() == View.GONE) {
                    mOptionsParent.setVisibility(View.VISIBLE);
                    // two child join  invest
                    addOptionChild(location, floatWidth);
                    optionsShow(centerX, centerY);
                } else {
                    optionsHide();
                }
                break;
            }
        }
    }

    private void addOptionChild(int[] location, int floatWidth) {
        if (mOptionsParent != null && mOptionsParent.getChildCount() > 0) {
            mOptionsParent.removeAllViews();
        }

        float centerX = location[0] + floatWidth / 2;
        float centerY = location[1] + 20;

        int left = location[0] - mFabMargin / 2;
        int top = location[1] - mFloatingMarginTop;
        int right = location[0] + floatWidth + mFabMargin / 2;
        int bottom = location[1] + mFloatingMarginTop + mFabMargin;
        int outerLeft = left - mOuterRectFMargin;
        int outerTop = top - mOuterRectFMargin;
        int outerRight = right + mOuterRectFMargin;
        int outerBottom = bottom + mOuterRectFMargin;
        RelativeLayout.LayoutParams optionParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RectF inner = new RectF(left, top, right, bottom);
        RectF outer = new RectF(outerLeft, outerTop, outerRight, outerBottom);
        RectF middle = new RectF(outerLeft + (left - outerLeft) / 2, outerTop + (top - outerTop) / 2, outerRight + (right - outerRight) / 2, outerBottom + (bottom - outerBottom) / 2);

        // child one join us
        FloatingOptionView optionJoin = new FloatingOptionView(this);
        RectF topRect = new RectF(left, outerTop, right, top + mFabMargin);
        optionJoin.setTopOuter(topRect);
        optionJoin.setDrawText(ResUtils.getString(this, R.string.home_page_detail_join));
        optionJoin.setInner(inner);
        optionJoin.setOuter(outer);
        optionJoin.setMiddleRectF(middle);
        optionJoin.setLayoutParams(optionParams);
        optionJoin.setOptionDirection(FloatingOptionView.OptionDirection.TOP);
        optionJoin.setCenter(centerX, centerY, floatWidth, mFabMargin);

        // child two invest
        FloatingOptionView optionInvest = new FloatingOptionView(this);
        RectF leftRect = new RectF(outerLeft, top, left + mFabMargin, bottom);
        optionInvest.setLeftOuter(leftRect);
        optionInvest.setDrawText(ResUtils.getString(this, R.string.home_page_detail_invest));
        optionInvest.setInner(inner);
        optionInvest.setOuter(outer);
        optionInvest.setMiddleRectF(middle);
        optionInvest.setLayoutParams(optionParams);
        optionInvest.setOptionDirection(FloatingOptionView.OptionDirection.LEFT);
        optionInvest.setCenter(centerX, centerY, floatWidth, mFabMargin);

        // child three connect us
        FloatingOptionView optionConnectUs = new FloatingOptionView(this);
        RectF bottomRect = new RectF(left, bottom - mFabMargin, right, outerBottom);
        optionConnectUs.setBottomOuter(bottomRect);
        optionConnectUs.setDrawText(ResUtils.getString(this, R.string.home_page_detail_connect_us));
        optionConnectUs.setInner(inner);
        optionConnectUs.setOuter(outer);
        optionConnectUs.setMiddleRectF(middle);
        optionConnectUs.setLayoutParams(optionParams);
        optionConnectUs.setOptionDirection(FloatingOptionView.OptionDirection.BOTTOM);
        optionConnectUs.setCenter(centerX, centerY, floatWidth, mFabMargin);

        optionJoin.setIClickListener(this);
        optionInvest.setIClickListener(this);
        optionConnectUs.setIClickListener(this);


        mOptionsParent.addView(optionJoin);
        mOptionsParent.addView(optionInvest);
        mOptionsParent.addView(optionConnectUs);
    }

    /// down load test
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
            List<HomeItemDetailBannerModel> images = detailsObj.getDetailsImgs();
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

    private void optionsShow(float centerX, float centerY) {
//        float pivotX = centerX * 1f / mScreenWidth;
//        float pivotY = centerY * 1f / mScreenHeight + .01f;
        for (int i = 0; i < mOptionsParent.getChildCount(); ++i) {
            FloatingOptionView optionView = (FloatingOptionView) mOptionsParent.getChildAt(i);
            ViewHelper.setPivotX(optionView, centerX);
            ViewHelper.setPivotY(optionView, centerY);
            ViewHelper.setRotation(optionView, i * 90f);
            ObjectAnimator rotate = ObjectAnimator.ofFloat(optionView, "rotation", i * 90f, 0f);
            rotate.setDuration(i * 200);
            rotate.setInterpolator(new LinearInterpolator());
            rotate.start();
        }


    }

    private void optionsHide() {
        for (int i = mOptionsParent.getChildCount() - 1; i >= 0; --i) {
            FloatingOptionView invest =  (FloatingOptionView) mOptionsParent.getChildAt(i);
            ObjectAnimator rotate = ObjectAnimator.ofFloat(invest, "rotation", 0f, i * 90f);
            rotate.setDuration(i * 200);
            rotate.setInterpolator(new LinearInterpolator());
            rotate.start();
            if (i == mOptionsParent.getChildCount() - 1) {
                rotate.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                        super.onAnimationEnd(animation);
                        mOptionsParent.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    @Override
    public void onClickDown(FloatingOptionView.OptionDirection direction) {
        int downColor = Color.parseColor("#20FF4081");
        int txtColor = Color.parseColor("#FFFFFF");
        switch (direction) {
            case LEFT: {
                FloatingOptionView leftView = (FloatingOptionView) mOptionsParent.getChildAt(1);
                leftView.setPaintColor(downColor, txtColor);
                leftView.postInvalidate();
                break;
            }
            case TOP: {
                FloatingOptionView leftView = (FloatingOptionView) mOptionsParent.getChildAt(0);
                leftView.setPaintColor(downColor, txtColor);
                leftView.postInvalidate();
                break;
            }
            case BOTTOM: {
                FloatingOptionView leftView = (FloatingOptionView) mOptionsParent.getChildAt(2);
                leftView.setPaintColor(downColor, txtColor);
                leftView.postInvalidate();
                break;
            }
        }
    }

    @Override
    public void onClickUp(FloatingOptionView.OptionDirection direction) {
        int upColor = Color.parseColor("#CCFFFFFF");
        int txtColor = Color.parseColor("#CCFF4081");
        switch (direction) {
            case LEFT: {
                FloatingOptionView leftView = (FloatingOptionView) mOptionsParent.getChildAt(1);
                leftView.setPaintColor(upColor, txtColor);
                leftView.postInvalidate();
                Toast.makeText(this, "invest us", Toast.LENGTH_LONG).show();
                break;
            }
            case TOP: {
                FloatingOptionView topView = (FloatingOptionView) mOptionsParent.getChildAt(0);
                topView.setPaintColor(upColor, txtColor);
                topView.postInvalidate();
                Toast.makeText(this, "join us", Toast.LENGTH_LONG).show();
                break;
            }
            case BOTTOM: {
                FloatingOptionView bottomView = (FloatingOptionView) mOptionsParent.getChildAt(2);
                bottomView.setPaintColor(upColor, txtColor);
                bottomView.postInvalidate();
                Toast.makeText(this, "connect us", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    @Override
    public void closeOptions() {
        optionsHide();
    }
}
