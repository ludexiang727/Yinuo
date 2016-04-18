package com.yinuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.adapter.base.RecyclerViewHolder;
import com.yinuo.mode.PartnerRecyclerModel;

import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class PartnerRecyclerViewAdapter <T extends PartnerRecyclerModel> extends BaseRecyclerAdapter {

    private LayoutInflater mInflater;
    private List<T> mModels;

    public PartnerRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void loadData(List<T> lists) {
        mModels = lists;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.partner_page_holder_view_layout, null);
        PartnerViewHolder holder = new PartnerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return null == mModels ? 0 : mModels.size();
    }

    @Override
    protected <E extends RecyclerViewHolder> void bindView(E viewHolder, int position) {
        final PartnerViewHolder holder = (PartnerViewHolder) viewHolder;
        PartnerRecyclerModel model = mModels.get(position);
        loadImage(model.getBannerOrImgURL(), holder.imgView);
        holder.name.setText(model.getPartnerName());
    }

    private final class PartnerViewHolder extends RecyclerViewHolder {
        private ImageView imgView;
        private TextView name;
        public PartnerViewHolder(View view) {
            super(view);

            imgView = (ImageView) view.findViewById(R.id.partner_img);
            name = (TextView) view.findViewById(R.id.partner_name);
        }
    }
}