package com.yinuo.adapter.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yinuo.mode.LoanGridViewModel;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/19.
 */
public abstract class SuperAdapter <T extends SuperViewHolder> extends BaseAdapter {

    protected abstract void bindView(SuperViewHolder superHolder, LoanGridViewModel model);
    protected abstract T getViewHolder();
    protected abstract View getView();
    protected abstract void initHolder(SuperViewHolder holder, View view);
    protected abstract List<LoanGridViewModel> getList();

    protected LayoutInflater mInflater;

    public SuperAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SuperViewHolder holder;
        if (convertView == null) {
            holder = getViewHolder();
            convertView = getView();
            initHolder(holder, convertView);

            convertView.setTag(holder);
        } else {
            holder = (SuperViewHolder) convertView.getTag();
        }
        bindView(holder, getList().get(position));
        return convertView;
    }
}
