package com.yinuo.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yinuo.R;
import com.yinuo.listener.ILocationView;

/**
 * Created by ludexiang on 2016/4/27.
 */
public class LocationView extends RelativeLayout {

    private Context mContext;
    private LayoutInflater mInflater;
    private RelativeLayout mViewParent;
    private LocationFragment mFragment;
    private ILocationView iLocationView;
    private int mOptionId;

    public LocationView(Context context) {
        this(context, null);
    }

    public LocationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LocationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LocationView);
        mOptionId = array.getResourceId(R.styleable.LocationView_content_id, 0);
        array.recycle();

        mContext = context;
        mInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        View view = mInflater.inflate(R.layout.app_location_view_layout, this, true);
        mFragment = new LocationFragment();
        mViewParent = (RelativeLayout) view.findViewById(R.id.app_location_view_parent);
    }

    public void setLocationListener(ILocationView listener) {
        iLocationView = listener;
    }

    public void showOptions() {
        if (mOptionId == 0) {
            mViewParent.setVisibility(View.GONE);
        } else {
            mViewParent.setVisibility(View.VISIBLE);
            View view = findViewById(mOptionId);
            if (view != null) {
                if (getChildCount() > 0) {
                    removeView(view);
                }
                mViewParent.addView(view);
            }
            if (iLocationView != null) {
                iLocationView.initView(view);
            }
        }
    }
}
