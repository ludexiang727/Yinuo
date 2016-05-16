package com.yinuo.ui.sub.workspace;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.animation.ValueAnimator;
import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.helper.PopupWindowHelper;
import com.yinuo.ui.component.widget.view.PullToRefreshLayout;
import com.yinuo.ui.component.widget.view.WorkspaceTenementListView;
import com.yinuo.utils.AppUtils;
import com.yinuo.utils.ResUtils;

/**
 * Created by gus on 16/5/15.
 * tenement -- 租房
 */
public class WorkspaceTenementActivity extends BaseActivity implements View.OnClickListener,
        PullToRefreshLayout.OnRefreshListener {

    private LayoutInflater mInflater;
    private final int SHOW_POP_WINDOW_TIME = 300;
    private int mScreenHeight;

    private EditText mWorkspaceTenementEdit;
    private ImageView mWorkspaceTenementBack;
    private PullToRefreshLayout mPullRefreshLayout;
    private WorkspaceTenementListView mListView;
    private TextView mConditionAreaView;
    private TextView mConditionPriceView;
    private TextView mConditionMoreView;

    private View mPopView;
    private ListView mConditionLeftList;
    private ListView mConditionRightList;
    private PopupWindowHelper mPopHelper;
    private View mDropView;
    private int mPopWindowHeight;
    private int mPopYOffset;

    @Override
    protected int getTitleLayout() {
        return R.layout.workspace_tenement_title_layout;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.workspace_tenement_layout;
    }

    @Override
    protected void initTitleView(View titleView) {
        mDropView = titleView;

        mWorkspaceTenementBack = (ImageView) titleView.findViewById(R.id.workspace_tenement_title_left);
        mWorkspaceTenementEdit = (EditText) titleView.findViewById(R.id.workspace_tenement_title_edit);
        mConditionAreaView = (TextView) titleView.findViewById(R.id.workspace_tenement_condition_area);
        mConditionPriceView = (TextView) titleView.findViewById(R.id.workspace_tenement_condition_price);
        mConditionMoreView = (TextView) titleView.findViewById(R.id.workspace_tenement_condition_more);
        mWorkspaceTenementBack.setOnClickListener(this);
        mConditionAreaView.setOnClickListener(this);
        mConditionPriceView.setOnClickListener(this);
        mConditionMoreView.setOnClickListener(this);

        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mDropView.measure(width, height);
        mPopWindowHeight = mScreenHeight - mDropView.getMeasuredHeight() * 2 - mPopYOffset;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreenHeight = AppUtils.obtainScreenWidthAndHeight(this)[1];
        mPopYOffset = ResUtils.getDimen(this, R.dimen.workspace_tenement_pop_window_y_offset);

        showTitle(true);
        mInflater = LayoutInflater.from(this);
        mPopView = mInflater.inflate(R.layout.workspace_tenement_condition_view_layout, null);
        mPopHelper = PopupWindowHelper.getInstance();

        mConditionLeftList = (ListView) mPopView.findViewById(R.id.workspace_tenement_condition_left_list);
        mConditionRightList = (ListView) mPopView.findViewById(R.id.workspace_tenement_condition_right_list);
    }

    @Override
    protected void loadView(View view) {
        mPullRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.workspace_tenement_pull_refresh_layout);
        mListView = (WorkspaceTenementListView) view.findViewById(R.id.workspace_tenement_list_view);

        mPullRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        dismissLoading();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.workspace_tenement_title_left: {
                finish();
                break;
            }
            case R.id.workspace_tenement_condition_area: {
                if (mPopHelper.isShowing()) {
                    heightAnim(true);
                } else {
                    heightAnim(false);
                }
                break;
            }
            case R.id.workspace_tenement_condition_price: {
                if (mPopHelper.isShowing()) {
                    heightAnim(true);
                } else {
                    heightAnim(false);
                }
                break;
            }
            case R.id.workspace_tenement_condition_more: {
                if (mPopHelper.isShowing()) {
                    heightAnim(true);
                } else {
                    heightAnim(false);
                }
                break;
            }
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        pullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
    }

    /** show popup window anim -- 展示 popupwindow 动画*/
    private void heightAnim(boolean isReverse) {
        if (isReverse) {
            mPopHelper.dismiss();
        } else {
            mPopHelper.setPopWH(ViewGroup.LayoutParams.MATCH_PARENT, mPopWindowHeight);
            mPopHelper.showPopInLocation(mPopView, mDropView, Gravity.TOP, 0, mDropView.getMeasuredHeight() + mPopYOffset);
        }
        ValueAnimator height = ValueAnimator.ofFloat(isReverse ? mPopWindowHeight : 0, isReverse ? 0 : mPopWindowHeight);
        height.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float height = (Float) animation.getAnimatedValue();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mPopView.getLayoutParams();
                params.height = height.intValue();
                mPopHelper.updatePopHeight(height.intValue());
                mPopView.setLayoutParams(params);
            }
        });
        height.setDuration(SHOW_POP_WINDOW_TIME);
        height.start();
    }
}
