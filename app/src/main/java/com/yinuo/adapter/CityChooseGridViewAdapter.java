package com.yinuo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.SuperAdapter;
import com.yinuo.adapter.base.SuperViewHolder;
import com.yinuo.base.BaseObject;
import com.yinuo.listener.IOnItemClickListener;
import com.yinuo.mode.AddressModel;

import java.util.List;

/**
 * Created by ludexiang on 2016/5/11.
 */
public class CityChooseGridViewAdapter extends SuperAdapter {
    private List<AddressModel> mItems;

    public CityChooseGridViewAdapter(Context context) {
        super(context);
    }

    public void setList(List<AddressModel> list) {
        mItems = list;
    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected List<AddressModel> getList() {
        return mItems;
    }

    @Override
    protected View getView(int position) {
        return mInflater.inflate(R.layout.city_choose_page_grid_view_item_layout, null);
    }

    @Override
    protected SuperViewHolder getViewHolder() {
        return new CityChooseGridViewHolder();
    }

    @Override
    protected void initHolder(SuperViewHolder superHolder, int position, View view) {
        if (superHolder instanceof CityChooseGridViewHolder) {
            CityChooseGridViewHolder holder = (CityChooseGridViewHolder) superHolder;
            holder.city = (TextView) view.findViewById(R.id.city_choose_page_grid_view_item_city);
        }
    }

    @Override
    public void setItemClickListener(IOnItemClickListener listener) {

    }

    @Override
    protected void bindView(SuperViewHolder superHolder, BaseObject base) {
        if (superHolder instanceof CityChooseGridViewHolder) {
            CityChooseGridViewHolder holder = (CityChooseGridViewHolder) superHolder;
            if (base instanceof AddressModel) {
                AddressModel model = (AddressModel) base;
                holder.city.setText(model.getCityName());
            }
        }
    }

    private final class CityChooseGridViewHolder extends SuperViewHolder {
        private int mPosition;
        private TextView city;

        @Override
        protected void setPosition(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View view) {

        }
    }
}
