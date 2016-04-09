package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yinuo.adapter.HomePageListViewAdapter;
import com.yinuo.mode.HomePageDataMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class HomePageListView extends ListView implements AdapterView.OnItemClickListener {
    private HomePageListViewAdapter mPageAdapter;
    private Context mContext;
    private List<HomePageDataMode> mCardLists = null;

    public HomePageListView(Context context) {
        this(context, null);
    }

    public HomePageListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mPageAdapter = new HomePageListViewAdapter(mContext);
        setOnItemClickListener(this);
    }

    public void setCardLists(List<HomePageDataMode> cards) {
        if (cards != null) {
            mCardLists = cards;
        }

        mPageAdapter.setCards(mCardLists);
        setAdapter(mPageAdapter);
    }

    public HomePageListViewAdapter getPageAdapter() {
        return mPageAdapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
