package com.yinuo.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.yinuo.R;
import com.yinuo.adapter.base.SuperAdapter;
import com.yinuo.adapter.base.SuperViewHolder;
import com.yinuo.base.BaseApplication;
import com.yinuo.base.BaseObject;
import com.yinuo.helper.MapHelper;
import com.yinuo.listener.ILocation;
import com.yinuo.listener.IOnItemClickListener;
import com.yinuo.mode.AddressModel;
import com.yinuo.ui.component.widget.view.CityChooseRecentGridView;
import com.yinuo.utils.StringUtils;

import java.util.List;

/**
 * Created by ludexiang on 2016/5/10.
 */
public class CityChoosePageAdapter extends SuperAdapter {
    private final int VIEW_TYPE = 5;
    private Context mContext;

    private List<AddressModel> mModels;
    private List<AddressModel> mHotModels;
    private List<AddressModel> mRecentAccessModels;

    public CityChoosePageAdapter(Context context) {
        super(context);
        mContext = context;
    }

    /** default alpha city name style --- list */
    public void setList(List<AddressModel> list) {
        mModels = list;
    }

    public void setHotList(List<AddressModel> hotList) {
        mHotModels = hotList;
    }

    public void setRecentAccessList(List<AddressModel> recentList) {
        mRecentAccessModels = recentList;
    }

    @Override
    protected List<AddressModel> getList() {
        return mModels;
    }

    @Override
    public int getCount() {
        return mModels == null ? 0 : mModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        return position < 4 ? position : 4;
    }

    @Override
    protected View getView(int position) {
        if (position == 0) {
            // location
            return mInflater.inflate(R.layout.city_choose_page_location_layout, null);
        } else if (position == 1) {
            // recent access
            return mInflater.inflate(R.layout.city_choose_page_recent_hot_layout, null);
        } else if (position == 2) {
            // hot city
            return mInflater.inflate(R.layout.city_choose_page_recent_hot_layout, null);
        } else if (position == 3) {
            // all city just title
            return mInflater.inflate(R.layout.city_choose_page_all_city_layout, null);
        } else {
            // range city with alpha
            return mInflater.inflate(R.layout.city_choose_page_alpha_city_layout, null);
        }
    }

    @Override
    protected CityChooseViewHolder getViewHolder() {
        return new CityChooseViewHolder();
    }

    @Override
    protected void initHolder(SuperViewHolder viewHolder, int position, View view) {
        if (viewHolder instanceof CityChooseViewHolder) {
            CityChooseViewHolder holder = (CityChooseViewHolder) viewHolder;
            if (position == 0) {
                holder.locationNotifyTxt = (TextView) view.findViewById(R.id.city_choose_page_notify);
                holder.locationCity = (TextView) view.findViewById(R.id.city_choose_page_location_city);
                holder.locationProgress = (ImageView) view.findViewById(R.id.city_choose_page_locating);
            } else if (position == 1 || position == 2) {
                holder.recentNotifyTxt = (TextView) view.findViewById(R.id.city_choose_page_recent_hot_notify);
                holder.recentGridView = (CityChooseRecentGridView) view.findViewById(R.id.city_choose_page_recent_hot_grid_view);
            } else if (position == 3) {
                holder.allCityNotify = (TextView) view.findViewById(R.id.city_choose_page_notify);
            } else {
                holder.allCityAlpha = (TextView) view.findViewById(R.id.city_choose_page_alpha);
                holder.allCityName = (TextView) view.findViewById(R.id.city_choose_page_city_name);
            }
        }
    }

    @Override
    public void setItemClickListener(IOnItemClickListener listener) {

    }

    @Override
    protected void bindView(SuperViewHolder superHolder, BaseObject base) {
        if (superHolder instanceof CityChooseViewHolder) {
            CityChooseViewHolder holder = (CityChooseViewHolder) superHolder;
            if (base instanceof AddressModel) {
                AddressModel model = (AddressModel) base;
                int position = mModels.indexOf(model);
                showView(holder, position);
            }
        }
    }

    private void showView(CityChooseViewHolder holder, int position) {
        switch (position) {
            case 0: {
                showLocationCity(holder);
                break;
            }
            case 1: {
                showRecentAccessCity(holder);
                break;
            }
            case 2: {
                showHotCity(holder);
                break;
            }
            case 3: {
                break;
            }
            default: {
                showAllCity(holder, position);
                break;
            }
        }
    }

    private void showLocationCity(CityChooseViewHolder holder) {
        AnimationDrawable animationDrawable = null;
        BDLocation bdLocation = BaseApplication.getInstance().getBDLocation();
        if (bdLocation != null) {
            if (animationDrawable != null && animationDrawable.isRunning()) {
                animationDrawable.stop();
            }
            holder.locationCity.setVisibility(View.VISIBLE);
            holder.locationNotifyTxt.setText(R.string.city_choose_page_current_location);
            holder.locationCity.setText(bdLocation.getCity());
            holder.locationProgress.setVisibility(View.GONE);
        } else {
            holder.locationNotifyTxt.setText(R.string.city_choose_page_locating);
            holder.locationCity.setVisibility(View.GONE);
            holder.locationProgress.setVisibility(View.VISIBLE);
            animationDrawable = (AnimationDrawable) holder.locationProgress.getDrawable();
            if (animationDrawable != null) {
                animationDrawable.start();
            }
        }
    }

    private void showRecentAccessCity(CityChooseViewHolder holder) {
        holder.recentNotifyTxt.setText(mContext.getString(R.string.city_choose_page_recent_access_city));
        holder.recentGridView.setList(mRecentAccessModels);
    }

    private void showHotCity(CityChooseViewHolder holder) {
        holder.recentNotifyTxt.setText(mContext.getString(R.string.city_choose_page_hot_city));
        holder.recentGridView.setList(mHotModels);
        holder.recentGridView.getGridViewAdapter().notifyDataSetChanged();
    }

    private void showAllCity(CityChooseViewHolder holder, int position) {
        AddressModel model = mModels.get(position);
        holder.allCityName.setText(model.getCityName());
        String currentStr = StringUtils.getAlpha(mContext, mModels.get(position).getCityPinYin());
        String previewStr = (position - 1) >= 0 ? StringUtils.getAlpha(mContext, mModels.get(position - 1).getCityPinYin()) : " ";
        if (!previewStr.equals(currentStr)) {
            holder.allCityAlpha.setVisibility(View.VISIBLE);
            holder.allCityAlpha.setText(currentStr);
        } else {
            holder.allCityAlpha.setVisibility(View.GONE);
        }
    }

    private final class CityChooseViewHolder extends SuperViewHolder {
        private TextView locationNotifyTxt;
        private ImageView locationProgress;
        private TextView locationCity;
        /** recent access city or hot city */
        private TextView recentNotifyTxt;
        private CityChooseRecentGridView recentGridView;
        /** all city */
        private TextView allCityNotify;
        private TextView allCityAlpha;
        private TextView allCityName;

        private int mPosition;

        @Override
        protected void setPosition(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View view) {

        }
    }

}
