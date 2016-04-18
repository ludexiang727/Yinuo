package com.yinuo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.adapter.base.RecyclerViewHolder;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class WorkspaceRecyclerViewAdapter extends BaseRecyclerAdapter {

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    protected <E extends RecyclerViewHolder> void bindView(E holder, int position) {

    }

    private final class WorkspaceViewHolder extends RecyclerViewHolder {
        private ImageView workspaceImg;
        private TextView workspaceTitle;
        private LinearLayout workspaceLocation;
        private TextView workspaceArea;
        private TextView workspaceRent;

        public WorkspaceViewHolder(View itemView) {
            super(itemView);
        }
    }
}
