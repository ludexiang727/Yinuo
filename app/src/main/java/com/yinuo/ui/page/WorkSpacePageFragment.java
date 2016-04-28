package com.yinuo.ui.page;

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
import com.yinuo.mode.WorkspaceOptionModel;
import com.yinuo.mode.WorkspacePageModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetWorkspacePageObj;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.component.widget.view.WorkspaceRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class WorkSpacePageFragment extends BaseFragment implements View.OnClickListener {

//    private TextView mWorkSpaceShop;
//    private TextView mWorkSpaceArea;
//    private TextView mWorkSpaceMore;
    private int mPageIndex = 1;
    // just request 30 items
    private final int PAGE_COUNT = 30;
    private LinearLayout mWorkspaceOptionParent;
    private WorkspaceRecyclerView mWorkspaceRecycleView;
    private UIHandler mHandler = new UIHandler();
    private List<WorkspacePageModel> mLists = new ArrayList<WorkspacePageModel>();
    private List<WorkspaceOptionModel> mOptions = new ArrayList<WorkspaceOptionModel>();
    private LayoutInflater mInflater;

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
        return view;
    }

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_workspace_page_layout;
    }

    @Override
    public void initialize(View view) {
        mLoading = (Loading) view.findViewById(R.id.workspace_page_loading);
//        mWorkSpaceShop = (TextView) view.findViewById(R.id.workspace_page_shop);
//        mWorkSpaceArea = (TextView) view.findViewById(R.id.workspace_page_area);
//        mWorkSpaceMore = (TextView) view.findViewById(R.id.workspace_page_more);
        mWorkspaceRecycleView = (WorkspaceRecyclerView) view.findViewById(R.id.workspace_page_recycle_view);
        mWorkspaceOptionParent = (LinearLayout) view.findViewById(R.id.workspace_page_option_parent);

//        mWorkSpaceShop.setOnClickListener(this);
//        mWorkSpaceArea.setOnClickListener(this);
//        mWorkSpaceMore.setOnClickListener(this);
        mWorkspaceRecycleView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mWorkspaceRecycleView.bindRecycleView(mLists);
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
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.workspace_page_shop: {
//                break;
//            }
//            case R.id.workspace_page_area: {
//                break;
//            }
//            case R.id.workspace_page_more: {
//                break;
//            }
//        }
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onItemClick(BaseObject baseObject, int position) {
        super.onItemClick(baseObject, position);
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.weight = 1;
        for (WorkspaceOptionModel optionModel : mOptions) {
            View view = mInflater.inflate(R.layout.workspace_options_layout, null);
            ImageView img = (ImageView) view.findViewById(R.id.workspace_page_option_img);
            TextView txt = (TextView) view.findViewById(R.id.workspace_page_option_txt);
            txt.setText(optionModel.getOptionTxt());
            mWorkspaceOptionParent.addView(view, params);
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
