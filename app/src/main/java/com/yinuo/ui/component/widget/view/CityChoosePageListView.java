package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.yinuo.adapter.CityChoosePageAdapter;
import com.yinuo.mode.AddressModel;

import java.util.List;

/**
 * Created by ludexiang on 2016/5/10.
 */
public class CityChoosePageListView extends ListView {

    private CityChoosePageAdapter mAdapter;

    public CityChoosePageListView(Context context) {
        this(context, null);
    }

    public CityChoosePageListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CityChoosePageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mAdapter = new CityChoosePageAdapter(context);
    }

    public void setList(List<AddressModel> defaultList) {
        mAdapter.setList(defaultList);
        setAdapter(mAdapter);
    }

    public void setHotList(List<AddressModel> hotList) {
        mAdapter.setHotList(hotList);
    }

    public void setRecentList(List<AddressModel> recentList) {
        mAdapter.setRecentAccessList(recentList);
    }

    public CityChoosePageAdapter getCityChooseAdapter() {
        return mAdapter;
    }
}
