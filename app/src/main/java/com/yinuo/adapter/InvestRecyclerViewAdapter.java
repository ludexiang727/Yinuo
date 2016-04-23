package com.yinuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.adapter.base.RecyclerViewHolder;
import com.yinuo.base.BaseObject;
import com.yinuo.mode.InvestPageDataModel;
import com.yinuo.utils.ResUtils;

import java.util.List;


/**
 * Created by gus on 16/4/23.
 */
public class InvestRecyclerViewAdapter<T extends BaseObject> extends BaseRecyclerAdapter {
    private LayoutInflater mInflater;
    private List<T> mLists;
    private Context mContext;

    public InvestRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void loadData(List<T> lists) {
        mLists = lists;
    }

    @Override
    public int getItemCount() {
        return null == mLists ? 0 : mLists.size();
    }

    @Override
    public InvestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.invest_recycler_view_holder_layout, null);
        InvestViewHolder holder = new InvestViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindView(holder, position);
    }


    @Override
    protected <E extends RecyclerViewHolder> void bindView(E viewHolder, int position) {
        InvestViewHolder holder = (InvestViewHolder) viewHolder;
        if (position < mLists.size() && mLists.get(position) instanceof InvestPageDataModel) {
            InvestPageDataModel model = (InvestPageDataModel) mLists.get(position);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            if (position == 0) {
                int top = ResUtils.getInt(mContext, R.dimen.partner_page_condition_height);
                params.topMargin = top;
            } else {
                params.topMargin = 0;
            }
            holder.mView.setLayoutParams(params);
            loadImage(model.getInvestImg(), holder.imgView);
            holder.name.setText(model.getInvestName());
            holder.duty.setText(model.getInvestDuty());
            holder.province.setText(model.getInvestProvince());
            holder.city.setText(model.getInvestCity());
            holder.company.setText(model.getInvestCompany());
            holder.notice.setText(model.getInvestNotice());
        }

    }

    private final class InvestViewHolder extends RecyclerViewHolder {
        private View mView;
        private ImageView imgView;
        private TextView validate;
        private TextView name;
        private TextView duty;
        private TextView province;
        private TextView city;
        private TextView company;
        private TextView notice;

        public InvestViewHolder(View view) {
            super(view);

            mView = view;
            imgView = (ImageView) view.findViewById(R.id.invest_view_holder_img);
            validate = (TextView) view.findViewById(R.id.invest_view_holder_validate);
            name = (TextView) view.findViewById(R.id.invest_view_holder_name);
            duty = (TextView) view.findViewById(R.id.invest_view_holder_duty);
            province = (TextView) view.findViewById(R.id.invest_view_holder_province);
            city = (TextView) view.findViewById(R.id.invest_view_holder_city);
            company = (TextView) view.findViewById(R.id.invest_view_holder_company);
            notice = (TextView) view.findViewById(R.id.invest_view_holder_notice);
        }
    }
}
