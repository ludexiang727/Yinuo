package com.yinuo.ui.page;

import android.view.View;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseFragment;
import com.yinuo.ui.component.widget.Loading;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class WorkSpacePageFragment extends BaseFragment implements View.OnClickListener {

    private TextView mWorkSpaceShop;
    private TextView mWorkSpaceArea;
    private TextView mWorkSpaceMore;

    @Override
    public int pageLayoutId() {
        return R.layout.fragment_workspace_page_layout;
    }

    @Override
    public void initialize(View view) {
        mLoading = (Loading) view.findViewById(R.id.workspace_page_loading);
        mWorkSpaceShop = (TextView) view.findViewById(R.id.workspace_page_shop);
        mWorkSpaceArea = (TextView) view.findViewById(R.id.workspace_page_area);
        mWorkSpaceMore = (TextView) view.findViewById(R.id.workspace_page_more);

        mWorkSpaceShop.setOnClickListener(this);
        mWorkSpaceArea.setOnClickListener(this);
        mWorkSpaceMore.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        mLoading.loading();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.workspace_page_shop: {
                break;
            }
            case R.id.workspace_page_area: {
                break;
            }
            case R.id.workspace_page_more: {
                break;
            }
        }
    }
}
