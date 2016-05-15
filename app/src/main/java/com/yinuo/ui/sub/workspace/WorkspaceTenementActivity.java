package com.yinuo.ui.sub.workspace;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.ui.component.widget.view.WorkspaceTenementListView;

/**
 * Created by gus on 16/5/15.
 * tenement -- 租房
 */
public class WorkspaceTenementActivity extends BaseActivity implements View.OnClickListener {
    private EditText mWorkspaceTenementEdit;
    private ImageView mWorkspaceTenementBack;
    private WorkspaceTenementListView mListView;

    @Override
    protected int getTitleLayout() {
        return R.layout.workspace_tenement_title_layout;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.workspace_tenement_layout;
    }

    @Override
    protected void initTitleView(View titleView) {
        mWorkspaceTenementBack = (ImageView) titleView.findViewById(R.id.workspace_tenement_title_left);
        mWorkspaceTenementEdit = (EditText) titleView.findViewById(R.id.workspace_tenement_title_edit);
        mWorkspaceTenementBack.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitle(true);
    }

    @Override
    protected void loadView(View view) {
        mListView = (WorkspaceTenementListView) view.findViewById(R.id.workspace_tenement_list_view);
    }

    @Override
    protected void loadData() {
        dismissLoading();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.workspace_tenement_title_left: {
                finish();
                break;
            }
        }
    }
}
