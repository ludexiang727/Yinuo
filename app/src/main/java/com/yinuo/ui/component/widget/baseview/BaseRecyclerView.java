package com.yinuo.ui.component.widget.baseview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.base.BaseObject;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 */
public abstract class BaseRecyclerView extends RecyclerView {
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    public abstract <T extends BaseRecyclerAdapter> T getRecyclerAdapter();
    public abstract <T extends BaseObject> void bindRecycleView(List<T> lists);

    public BaseRecyclerView(Context context) {
        this(context, null);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return super.canScrollVertically(direction);
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout layout) {
        mSwipeRefreshLayout = layout;
    }
}
