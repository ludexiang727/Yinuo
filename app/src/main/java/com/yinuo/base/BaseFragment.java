package com.yinuo.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yinuo.Constants;
import com.yinuo.R;
import com.yinuo.listener.IDynamicLoadListener;
import com.yinuo.listener.IOnItemClickListener;
import com.yinuo.listener.ITransationSceneListener;
import com.yinuo.net.IRequestListener;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.ui.component.widget.VerticalSwipeRefreshLayout;

/**
 * Created by ludexiang on 2016/4/5.
 */
public abstract class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, IRequestListener<NetBaseObject>
        , IDynamicLoadListener, ITransationSceneListener, IOnItemClickListener {

    protected VerticalSwipeRefreshLayout mSwipeRefreshLayout;
    protected RelativeLayout mContentParent;
    protected ViewStub mViewStub;
    protected Loading mLoading;
    protected boolean isRefreshing;
    protected boolean isVisible;
    /** 标志位，标志已经初始化完成 */
    protected boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    protected boolean mHasLoadedOnce;
    protected static final String FRAGMENT_INDEX = "fragment_index";
    private UIHandler mHandler = new UIHandler();
    private View mStubView;
    private ImageView mStubImg;
    private TextView mStubTxt;

    public abstract int pageLayoutId();
    public abstract void initialize(View view);
    public abstract void loadData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment_layout, null);
        mSwipeRefreshLayout = (VerticalSwipeRefreshLayout) view.findViewById(R.id.base_fragment_swipe_refresh);
        mContentParent = (RelativeLayout) view.findViewById(R.id.base_fragment_parent_content);
        mViewStub = (ViewStub) view.findViewById(R.id.base_fragment_view_stub);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_color_progress1, R.color.refresh_color_progress2);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        View content = inflater.inflate(pageLayoutId(), mContentParent, true);
        initialize(content);
        return view;
    }

    private final class UIHandler extends Handler {
        private final int NOTIFY_NET_WORK_ERROR = 0x100;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoading.dismiss();
            switch (msg.what) {
                case NOTIFY_NET_WORK_ERROR: {
                    if (mStubView == null) {
                        mStubView = mViewStub.inflate();
                        mStubImg = (ImageView) mStubView.findViewById(R.id.base_fragment_view_stub_layout_err_img);
                        mStubTxt = (TextView) mStubView.findViewById(R.id.base_fragment_view_stub_layout_err_msg);
                    } else {
                        mStubView.setVisibility(View.VISIBLE);
                    }
                    NetBaseObject obj = (NetBaseObject) msg.obj;
                    mStubTxt.setText(obj.getErrMsg());
                    break;
                }
            }
        }
    }

    @Override
    public void onFail(NetBaseObject object) {
        if (object.getErrorNo() == Constants.NET_WORK_ERROR) {
            Message msg = mHandler.obtainMessage();
            msg.what = mHandler.NOTIFY_NET_WORK_ERROR;
            msg.obj = object;
            msg.sendToTarget();
        }
    }

    @Override
    public void onSuccess(NetBaseObject object) {
    }

    @Override
    public void onDynamicRefresh() {
    }

    /** load more -- 加载更多 */
    @Override
    public void onLoadMore() {
    }

    @Override
    public void onItemClick(View view, BaseObject baseObject, int position) {
    }

    @Override
    public void onRefresh() {
    }

    /** Activity scene anim -- Activity 转场动画*/
    @Override
    public void onTransation(int position) {
    }

    protected void onVisible() {
        loadData();
    }
}
