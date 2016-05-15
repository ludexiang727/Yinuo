package com.yinuo.ui.component.widget.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yinuo.R;
import com.yinuo.utils.AppUtils;

/**
 * Created by ludexiang on 2016/5/13.
 */
public class BezierRefreshHeaderView extends FrameLayout {
    private LinearLayout mContainer;
    private AnimationDrawable mAnimDrawable;
    private ImageView mProgress;
    private BezierRefreshView mBazierRefreshView;
    private STATE mState = STATE.normal;
    private IStateChangedListener mStateChangedListener;

    private int stretchHeight;
    private int readyHeight;
    private static final int DISTANCE_BETWEEN_STRETCH_READY = 250;

    public enum STATE {
        normal,//正常
        stretch,//准备进行拉伸
        ready,//拉伸到最大位置
        refreshing,//刷新
        end//刷新结束，回滚
    }

    public BezierRefreshHeaderView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public BezierRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.app_refresh_bezier_layout, null);
        mProgress = (ImageView) mContainer.findViewById(R.id.app_bezier_refresh_loading);
        mBazierRefreshView = (BezierRefreshView) mContainer.findViewById(R.id.app_refresh_bezier_view);
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        addView(mContainer, lp);
        initHeight();
    }

    private void initHeight() {
        mContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                stretchHeight = mBazierRefreshView.getHeight();
                readyHeight = stretchHeight + DISTANCE_BETWEEN_STRETCH_READY;
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    /**
     * 修改状态。注：状态的改变与前一个状态以及下拉头高度有关
     *
     * @param state
     */
    public void updateState(STATE state) {
        if (state == mState) return;
        STATE oldState = mState;
        mState = state;
        if (mStateChangedListener != null) {
            mStateChangedListener.notifyStateChanged(oldState, mState);
        }

        switch (mState) {
            case normal:
                handleStateNormal();
                break;
            case stretch:
                handleStateStretch();
                break;
            case ready:
                handleStateReady();
                break;
            case refreshing:
                handleStateRefreshing();
                break;
            case end:
                handleStateEnd();
                break;
            default:
        }
    }

    /**
     * 处理处于normal状态的值
     */
    private void handleStateNormal() {
        mBazierRefreshView.setVisibility(View.VISIBLE);
        hideProgress();
        mContainer.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
    }

    /**
     * 处理水滴拉伸状态
     */
    private void handleStateStretch() {
        mBazierRefreshView.setVisibility(View.VISIBLE);
        hideProgress();
        mContainer.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
    }

    /**
     * 处理水滴ready状态，回弹效果
     */
    private void handleStateReady() {
        mBazierRefreshView.setVisibility(View.VISIBLE);
        hideProgress();
        Animator shrinkAnimator = mBazierRefreshView.createAnimator();
        shrinkAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //回弹结束后即进入refreshing状态
                updateState(STATE.refreshing);
            }
        });
        shrinkAnimator.start();//开始回弹
    }

    /**
     * 处理正在进行刷新状态
     */
    private void handleStateRefreshing() {
        mBazierRefreshView.setVisibility(View.GONE);
        showProgress();
    }

    /**
     * 处理刷新完毕状态
     */
    private void handleStateEnd() {
        mBazierRefreshView.setVisibility(View.GONE);
        hideProgress();
    }

    private void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
        if (mAnimDrawable == null) {
            mAnimDrawable = (AnimationDrawable) mProgress.getDrawable();
        }
        if (mAnimDrawable.isRunning()) {
            mAnimDrawable.stop();
        }
        mAnimDrawable.start();
    }

    private void hideProgress() {
        if (mAnimDrawable != null) {
            mAnimDrawable.stop();
        }
        mProgress.setVisibility(View.GONE);
    }

    public void setVisiableHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
        //通知水滴进行更新
        if (mState == STATE.stretch) {
            float pullOffset = (float) AppUtils.mapValueFromRangeToRange(height, stretchHeight, readyHeight, 0, 1);
            if (pullOffset < 0 || pullOffset > 1) {
                throw new IllegalArgumentException("pullOffset should between 0 and 1!" + mState + " " + height);
            }
            Log.e("pullOffset", "pullOffset:" + pullOffset);
            mBazierRefreshView.updateComleteState(pullOffset);
        }
    }

    public int getVisiableHeight() {
        return mContainer.getHeight();
    }

    public STATE getCurrentState() {
        return mState;
    }

    public int getStretchHeight() {
        return stretchHeight;
    }

    public int getReadyHeight() {
        return readyHeight;
    }

    public void setStateChangedListener(IStateChangedListener l) {
        mStateChangedListener = l;
    }

    public interface IStateChangedListener {
        public void notifyStateChanged(STATE oldState, STATE newState);
    }
}