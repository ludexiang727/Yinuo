package com.yinuo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.SuperAdapter;
import com.yinuo.adapter.base.SuperViewHolder;
import com.yinuo.mode.LoanGridViewModel;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/19.
 */
public class LoanGridViewAdapter extends SuperAdapter {

    private List<LoanGridViewModel> mItems;

    public LoanGridViewAdapter(Context context) {
        super(context);
    }

    public void setItems(List<LoanGridViewModel> models) {
        mItems = models;
    }

    @Override
    public List<LoanGridViewModel> getList() {
        return mItems;
    }

    @Override
    public int getCount() {
        return null == mItems ? 0 : mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mItems ? null : mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new ViewHolder();
    }

    @Override
    public View getView() {
        return mInflater.inflate(R.layout.loan_page_item_view_layout, null);
    }

    @Override
    protected void initHolder(SuperViewHolder superHolder, View view) {
        if (superHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) superHolder;
            holder.itemImg = (ImageView) view.findViewById(R.id.loan_item_view_img);
            holder.itemInfo = (TextView) view.findViewById(R.id.loan_item_view_txt);
            holder.itemHot = (ImageView) view.findViewById(R.id.loan_item_view_hot);
        }
    }

    @Override
    protected void bindView(SuperViewHolder superHolder, LoanGridViewModel model) {
        if (superHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) superHolder;
            holder.itemInfo.setText(model.getItemOption());
        }
    }

    private class ViewHolder extends SuperViewHolder {
        private ImageView itemImg;
        private TextView itemInfo;
        private ImageView itemHot;
    }
}
