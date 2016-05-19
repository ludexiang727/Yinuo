package com.yinuo.ui.sub.workspace;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.animation.ValueAnimator;
import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.base.BaseObject;
import com.yinuo.helper.DBHelper;
import com.yinuo.helper.PopupWindowHelper;
import com.yinuo.mode.AddressModel;
import com.yinuo.ui.component.widget.view.PullToRefreshLayout;
import com.yinuo.ui.component.widget.view.WorkspaceTenementListView;
import com.yinuo.utils.AppUtils;
import com.yinuo.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gus on 16/5/15.
 * tenement -- 租房
 */
public class WorkspaceTenementActivity <T extends BaseObject> extends BaseActivity implements View.OnClickListener,
        PullToRefreshLayout.OnRefreshListener {

    private LayoutInflater mInflater;
    private final int SHOW_POP_WINDOW_TIME = 300;
    private int mScreenHeight;
    private UiHandler mHandler = new UiHandler();
    private int mType;

    private EditText mWorkspaceTenementEdit;
    private ImageView mWorkspaceTenementBack;
    private PullToRefreshLayout mPullRefreshLayout;
    private WorkspaceTenementListView mListView;
    private TextView mConditionItem1View;
    private TextView mConditionItem2View;
    private TextView mConditionMoreView;

    private View mPopView;
    private ListView mConditionLeftList;
    private ListView mConditionRightList;
    private PopupWindowHelper mPopHelper;
    private View mDropView;
    private int mPopWindowHeight;
    private int mPopYOffset;

    private List<BaseObject> mLeftList = new ArrayList<BaseObject>();
    private List<BaseObject> mRightList = new ArrayList<BaseObject>();
    private LeftListAdapter mLeftListAdapter;
    private RightListAdapter mRightListAdapter;
    private DBHelper mDBHelper;

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
        mConditionItem1View = (TextView) titleView.findViewById(R.id.workspace_tenement_condition_item1);
        mConditionItem2View = (TextView) titleView.findViewById(R.id.workspace_tenement_condition_item2);
        mConditionMoreView = (TextView) titleView.findViewById(R.id.workspace_tenement_condition_more);
        mWorkspaceTenementBack.setOnClickListener(this);
        mConditionItem1View.setOnClickListener(this);
        mConditionItem2View.setOnClickListener(this);
        mConditionMoreView.setOnClickListener(this);

        if (mType == 1) {
            // tenement house
            mConditionItem1View.setText(R.string.workspace_tenement_condition_area);
            mConditionItem2View.setText(R.string.workspace_tenement_condition_price);
        } else if (mType == 2) {
            // tenement shop
            mConditionItem1View.setText(R.string.workspace_tenement_condition_shop);
            mConditionItem2View.setText(R.string.workspace_tenement_condition_area);
        }

        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mDropView.measure(width, height);
        mPopWindowHeight = mScreenHeight - mDropView.getMeasuredHeight() * 2 - mPopYOffset;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 1);
        super.onCreate(savedInstanceState);
        mScreenHeight = AppUtils.obtainScreenWidthAndHeight(this)[1];
        mPopYOffset = ResUtils.getDimen(this, R.dimen.workspace_tenement_pop_window_y_offset);

        mDBHelper = new DBHelper(this);
        showTitle(true);
        mInflater = LayoutInflater.from(this);
        mPopView = mInflater.inflate(R.layout.workspace_tenement_condition_view_layout, null);
        mPopHelper = PopupWindowHelper.getInstance();

        mConditionLeftList = (ListView) mPopView.findViewById(R.id.workspace_tenement_condition_left_list);
        mConditionRightList = (ListView) mPopView.findViewById(R.id.workspace_tenement_condition_right_list);

        mLeftListAdapter = new LeftListAdapter(this);
        mRightListAdapter = new RightListAdapter(this);

        mLeftListAdapter.setItems(mLeftList);
        mRightListAdapter.setItems(mRightList);

        mConditionLeftList.setAdapter(mLeftListAdapter);
        mConditionRightList.setAdapter(mRightListAdapter);
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
            case R.id.workspace_tenement_condition_item1: {
                if (mPopHelper.isShowing()) {
                    heightAnim(true);
                } else {
                    heightAnim(false);
                }
                if (mType == 1) {
                    List<AddressModel> models = mDBHelper.getAreaByCityId(1);
                    Message msg = mHandler.obtainMessage();
                    msg.obj = models;
                    msg.what = mHandler.NOTIFY_AREA_SUCCESS;
                    msg.sendToTarget();
                }
                break;
            }
            case R.id.workspace_tenement_condition_item2: {
                if (mPopHelper.isShowing()) {
                    heightAnim(true);
                } else {
                    heightAnim(false);
                }
                if (mType == 2) {
                    List<AddressModel> models = mDBHelper.getAreaByCityId(1);
                    Message msg = mHandler.obtainMessage();
                    msg.obj = models;
                    msg.what = mHandler.NOTIFY_AREA_SUCCESS;
                    msg.sendToTarget();
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

    private final class UiHandler extends Handler {
        private final int NOTIFY_AREA_SUCCESS = 0x000;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NOTIFY_AREA_SUCCESS: {
                    if (mLeftList.size() > 0) {
                        mLeftList.clear();
                    }
                    List<AddressModel> model = (List<AddressModel>) msg.obj;
                    if (model != null) {
                        mLeftList.addAll(model);
                    }
                    mLeftListAdapter.notifyDataSetChanged();
                    break;
                }
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
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                params.height = height.intValue();
                mPopHelper.updatePopHeight(height.intValue());
                mPopView.setLayoutParams(params);
            }
        });
        height.setDuration(SHOW_POP_WINDOW_TIME);
        height.start();
    }

    /***
     * left list view adapter
     */
    private final class LeftListAdapter extends BaseAdapter {

        private List<BaseObject> leftItems;
        private LayoutInflater inflater;

        public LeftListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setItems(List<BaseObject> items) {
            leftItems = items;
        }

        @Override
        public int getCount() {
            return leftItems == null ? 0 : leftItems.size();
        }

        @Override
        public Object getItem(int position) {
            return leftItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = inflater.inflate(R.layout.workspace_tenement_popup_window_item_layout, null);
                viewHolder.itemName = (TextView) view.findViewById(R.id.workspace_tenement_items_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            bindView(viewHolder, position, mLeftList, 0);
            return view;
        }


    }

    /** right list adpater */
    private final class RightListAdapter extends BaseAdapter {

        private List<BaseObject> rightItems;
        private LayoutInflater inflater;

        public RightListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        private void setItems(List<BaseObject> items) {
            rightItems = items;
        }

        @Override
        public int getCount() {
            return rightItems == null ? 0 : rightItems.size();
        }

        @Override
        public Object getItem(int position) {
            return rightItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.workspace_tenement_popup_window_item_layout, null);
                holder.itemName = (TextView) view.findViewById(R.id.workspace_tenement_items_name);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            bindView(holder, position, rightItems, 1);
            return view;
        }

    }

    private final class ViewHolder {
        private TextView itemName;
    }

    /**
     * @param holder
     * @param position
     * @param items
     * @param type 0 -- left 1 -- right
     */
    private void bindView(ViewHolder holder, int position, List<BaseObject> items, int type) {
        BaseObject t = items.get(position);
        switch (type) {
            case 0: {
                if (t instanceof AddressModel) {
                    AddressModel model = (AddressModel) t;
                    holder.itemName.setText(model.getCityAreaName());
                    if (position % 2 == 0) {
                        holder.itemName.setBackgroundColor(Color.WHITE);
                    } else {
                        holder.itemName.setBackgroundColor(Color.parseColor("#E3E3E3"));
                    }
                }
                break;
            }
            case 1: {
                break;
            }
        }

    }
}
