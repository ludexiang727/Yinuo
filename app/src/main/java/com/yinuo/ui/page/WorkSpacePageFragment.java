package com.yinuo.ui.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.base.BaseObject;
import com.yinuo.base.WebActivity;
import com.yinuo.mode.WorkspaceOptionModel;
import com.yinuo.mode.WorkspacePageModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetWorkspacePageObj;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.component.widget.view.WorkspaceRecyclerView;
import com.yinuo.ui.sub.workspace.WorkspaceTenementActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class WorkSpacePageFragment extends BaseFragment {

    private final int TENEMENT_HOUSE = 0x001;
    private final int TENEMENT_SHOP = 0x002;
    private int mPageIndex = 1;
    // just request 30 items
    private final int PAGE_COUNT = 30;
    private LinearLayout mWorkspaceOptionParent;
    private WorkspaceRecyclerView mWorkspaceRecycleView;
    private UIHandler mHandler = new UIHandler();
    private List<WorkspacePageModel> mLists = new ArrayList<WorkspacePageModel>();
    private List<WorkspaceOptionModel> mOptions = new ArrayList<WorkspaceOptionModel>();
    private LayoutInflater mInflater;
    private OptionsClickListener mClickListener;

    public static WorkSpacePageFragment newInstance(int index) {
        WorkSpacePageFragment fragment = new WorkSpacePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mInflater = LayoutInflater.from(getContext());
        isPrepared = true;
        loadData();
        mClickListener = new OptionsClickListener();
        return view;
    }

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_workspace_page_layout;
    }

    @Override
    public void initialize(View view) {
        mLoading = (Loading) view.findViewById(R.id.workspace_page_loading);
        mWorkspaceRecycleView = (WorkspaceRecyclerView) view.findViewById(R.id.workspace_page_recycle_view);
        mWorkspaceOptionParent = (LinearLayout) view.findViewById(R.id.workspace_page_option_parent);

        mWorkspaceRecycleView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mWorkspaceRecycleView.bindRecycleView(mLists);
        postOptions();
    }

    @Override
    public void loadData() {
        if (!isPrepared || mHasLoadedOnce) {
            return;
        }
        mLoading.loading();
        NetRequest.getInstance().requestWorkspacePageData(mPageIndex, PAGE_COUNT, this);
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        mHasLoadedOnce = false;
        isRefreshing = true;
        loadData();
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onItemClick(View view, BaseObject baseObject, int position) {
        super.onItemClick(view, baseObject, position);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetWorkspacePageObj) {
            mHasLoadedOnce = true;
            NetWorkspacePageObj obj = (NetWorkspacePageObj) object;
            Message msg = mHandler.obtainMessage();
            msg.what = mHandler.NOTIFY_SUCCESS;
            msg.obj = obj;
            msg.sendToTarget();
        }
    }

    private void postOptions() {
        if (mWorkspaceOptionParent.getChildCount() > 0 && mOptions.size() > 0) {
            mWorkspaceOptionParent.removeAllViews();
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.weight = 1;
        int position = 0;
        for (WorkspaceOptionModel optionModel : mOptions) {
            View view = mInflater.inflate(R.layout.workspace_options_layout, null);
            ImageView img = (ImageView) view.findViewById(R.id.workspace_page_option_img);
            TextView txt = (TextView) view.findViewById(R.id.workspace_page_option_txt);
            txt.setText(optionModel.getOptionTxt());
            mWorkspaceOptionParent.addView(view, params);
            view.setTag(position);
            position++;
            view.setOnClickListener(mClickListener);
        }
    }

    private class OptionsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            Intent intent = new Intent();
            switch (position) {
                case 0: {
                    // workspace tenement
                    intent.putExtra("type", TENEMENT_HOUSE);
                    intent.setClass(getContext(), WorkspaceTenementActivity.class);
                    break;
                }
                case 1: {
                    intent.putExtra("type", TENEMENT_SHOP);
                    intent.setClass(getContext(), WorkspaceTenementActivity.class);
                    break;
                }
                case 2: {
                    // intent must add WebModel
                    intent.setClass(getContext(), WebActivity.class);
                    break;
                }
                case 3: {
                    // intent must add WebModel
                    intent.setClass(getContext(), WebActivity.class);
                    break;
                }
            }
            startActivity(intent);
        }
    }

    private final class UIHandler extends Handler {
        private final int NOTIFY_SUCCESS = 0x000;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoading.dismiss();
            switch (msg.what) {
                case NOTIFY_SUCCESS: {
                    NetWorkspacePageObj obj = (NetWorkspacePageObj) msg.obj;
                    if (obj != null) {
                        if (mOptions != null && mOptions.size() == 0 && obj.getOptions() != null) {
                            mOptions.addAll(obj.getOptions());
                            postOptions();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (isRefreshing && mPageIndex == 1) {
                            mLists.clear();
                        }
                        List<WorkspacePageModel> models = obj.getModels();
                        if (models != null) {
                            mLists.addAll(models);
                            mWorkspaceRecycleView.getRecyclerAdapter().notifyDataSetChanged();
                        }
                    }
                    break;
                }
            }
        }
    }
}
