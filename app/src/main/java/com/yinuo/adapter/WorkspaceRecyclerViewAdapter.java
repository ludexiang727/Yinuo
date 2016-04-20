package com.yinuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.adapter.base.RecyclerViewHolder;
import com.yinuo.base.BaseObject;
import com.yinuo.mode.WorkspacePageModel;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class WorkspaceRecyclerViewAdapter <T extends BaseObject> extends BaseRecyclerAdapter {

    private List<T> mModels;
    private LayoutInflater mInflater;

    public WorkspaceRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void loadData(List<T> lists) {
        mModels = lists;
    }

    @Override
    public WorkspaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.workspace_page_view_holder_layout, null);
        WorkspaceViewHolder holder = new WorkspaceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return mModels == null ? 0 : mModels.size();
    }

    @Override
    protected <E extends RecyclerViewHolder> void bindView(E holder, int position) {

    }

    private final class WorkspaceViewHolder extends RecyclerViewHolder {
        private ImageView workspaceImg;
        private TextView workspaceTitle;
        private TextView workspaceLocation;
        private LinearLayout workspaceTags;
        private TextView workspaceRent;
        private LinearLayout workspaceExtras;

        public WorkspaceViewHolder(View view) {
            super(view);
            workspaceImg = (ImageView) view.findViewById(R.id.workspace_holder_img);
            workspaceTitle = (TextView) view.findViewById(R.id.workspace_holder_title);
            workspaceRent = (TextView) view.findViewById(R.id.workspace_holder_price);
            workspaceLocation = (TextView) view.findViewById(R.id.workspace_holder_location);
            workspaceTags = (LinearLayout) view.findViewById(R.id.workspace_holder_tags_parent);
            workspaceExtras = (LinearLayout) view.findViewById(R.id.workspace_holder_extras_parent);
        }
    }
}
