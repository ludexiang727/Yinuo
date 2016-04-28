package com.yinuo.ui.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class MorePageFragment extends BaseFragment {

    public static MorePageFragment newInstance(int index) {
        MorePageFragment fragment = new MorePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        isPrepared = true;
        loadData();
        return view;
    }

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_more_page_layout;
    }

    @Override
    public void initialize(View view) {

    }

    @Override
    public void loadData() {
        if (!isPrepared || mHasLoadedOnce) {
            return;
        }
    }

    @Override
    public void onRefresh() {

    }
}
