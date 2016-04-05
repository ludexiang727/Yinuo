package com.yinuo.ui.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.ui.component.widget.HomeListView;
import com.yinuo.ui.component.widget.Loading;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class HomePageFragment extends BaseFragment {

    private HomeListView mListView;
    @Override
    public int pageLayoutId() {
        return R.layout.fragment_home_page_layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void initialize(View view) {
        mLoading = (Loading) view.findViewById(R.id.home_page_loading);
        mLoading.loading();

        mListView = (HomeListView) view.findViewById(android.R.id.list);

    }
}
