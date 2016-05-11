package com.yinuo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.SuperAdapter;
import com.yinuo.adapter.base.SuperViewHolder;
import com.yinuo.base.BaseObject;
import com.yinuo.listener.IOnItemClickListener;
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

    public void setList(List<LoanGridViewModel> models) {
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
    public View getView(int position) {
        return mInflater.inflate(R.layout.loan_page_item_view_layout, null);
    }

    @Override
    protected void initHolder(SuperViewHolder superHolder, int position, View view) {
        if (superHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) superHolder;
            holder.itemImg = (ImageView) view.findViewById(R.id.loan_item_view_img);
            holder.itemInfo = (TextView) view.findViewById(R.id.loan_item_view_txt);
            holder.itemHot = (ImageView) view.findViewById(R.id.loan_item_view_hot);
        }
    }


    @Override
    protected void bindView(SuperViewHolder superHolder, BaseObject model) {
        if (superHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) superHolder;
            if (model instanceof LoanGridViewModel) {
                LoanGridViewModel loan = (LoanGridViewModel) model;
                holder.itemInfo.setText(loan.getItemOption());
            }
        }
    }

    @Override
    public void setItemClickListener(IOnItemClickListener listener) {
        itemClickListener = listener;
    }

    private class ViewHolder extends SuperViewHolder {
        private ImageView itemImg;
        private TextView itemInfo;
        private ImageView itemHot;
        private int position;

        @Override
        protected void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null && position < mItems.size()) {
                itemClickListener.onItemClick(v, mItems.get(position), position);
            }
        }
    }
}
