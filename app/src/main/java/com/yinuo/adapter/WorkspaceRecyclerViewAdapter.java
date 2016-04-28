package com.yinuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.adapter.base.RecyclerViewHolder;
import com.yinuo.base.BaseObject;
import com.yinuo.mode.WorkspacePageModel;
import com.yinuo.ui.component.widget.view.WorkspaceTagsView;
import com.yinuo.utils.ResUtils;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class WorkspaceRecyclerViewAdapter <T extends BaseObject> extends BaseRecyclerAdapter {

    private Context mContext;
    private List<T> mModels;
    private LayoutInflater mInflater;

    public WorkspaceRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
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
    protected <E extends RecyclerViewHolder> void bindView(E viewHolder, int position) {
        WorkspaceViewHolder holder = (WorkspaceViewHolder) viewHolder;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (position == 0) {
            int topMargin = ResUtils.getInt(mContext, R.dimen.workspace_holder_first_margin_top);
            params.topMargin = topMargin;
        } else {
            params.topMargin = 0;
        }
        holder.mView.setLayoutParams(params);
        if (position < mModels.size() && mModels.get(position) instanceof WorkspacePageModel) {
            WorkspacePageModel model = (WorkspacePageModel) mModels.get(position);
            loadImage(model.getWorkspaceImg(), holder.workspaceImg);
            holder.workspaceTitle.setText(model.getWorkspaceTitle());
            holder.workspaceRent.setText(model.getWorkspacePrice());
            holder.workspaceLocation.setText(model.getWorkspaceLocation());
            if (holder.workspaceTags.getChildCount() > 0) {
                holder.workspaceTags.removeAllViews();
            }
            for (String tag : model.getWorkspaceTags()) {
                WorkspaceTagsView tagView = new WorkspaceTagsView(mContext);
                tagView.setText(tag);
                holder.workspaceTags.addView(tagView);
            }

            if (holder.workspaceExtras.getChildCount() > 0) {
                holder.workspaceExtras.removeAllViews();
            }
            for (String extra : model.getWorkspaceExtra()) {
                WorkspaceTagsView extraView = new WorkspaceTagsView(mContext);
                extraView.setText(extra);
                holder.workspaceExtras.addView(extraView);
            }
        }
    }

    private final class WorkspaceViewHolder extends RecyclerViewHolder {
        private ImageView workspaceImg;
        private TextView workspaceTitle;
        private TextView workspaceLocation;
        private TextView workspaceRent;
        private LinearLayout workspaceTags;
        private LinearLayout workspaceExtras;
        private View mView;

        public WorkspaceViewHolder(View view) {
            super(view);

            mView = view;

            workspaceImg = (ImageView) mView.findViewById(R.id.workspace_holder_img);
            workspaceTitle = (TextView) mView.findViewById(R.id.workspace_holder_title);
            workspaceRent = (TextView) mView.findViewById(R.id.workspace_holder_price);
            workspaceLocation = (TextView) mView.findViewById(R.id.workspace_holder_location);
            workspaceTags = (LinearLayout) mView.findViewById(R.id.workspace_holder_tags_parent);
            workspaceExtras = (LinearLayout) mView.findViewById(R.id.workspace_holder_extras_parent);
        }
    }
}
