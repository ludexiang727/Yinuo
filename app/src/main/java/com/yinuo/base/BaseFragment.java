package com.yinuo.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
    protected Loading mLoading;
    protected boolean isRefreshing;
    protected boolean isVisible;
    /** 标志位，标志已经初始化完成 */
    protected boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    protected boolean mHasLoadedOnce;
    protected static final String FRAGMENT_INDEX = "fragment_index";

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

        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_color_progress1, R.color.refresh_color_progress2);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        View content = inflater.inflate(pageLayoutId(), mContentParent, true);
        initialize(content);
        return view;
    }

    @Override
    public void onFail(NetBaseObject object) {
    }

    @Override
    public void onSuccess(NetBaseObject object) {
    }

    @Override
    public void onDynamicRefresh() {

    }

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
