package com.yinuo.adapter;

import android.content.Context;
import android.util.Log;
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
import com.yinuo.mode.BossOnlineDataModel;
import com.yinuo.mode.BossOnlineWorkModel;
import com.yinuo.utils.ResUtils;
import com.yinuo.utils.StringUtils;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/21.
 */
public class BossOnlineRecyclerViewAdapter<T extends BaseObject> extends BaseRecyclerAdapter {
    private Context mContext;
    private List<T> mListHolder;
    private LayoutInflater mInflater;

    public BossOnlineRecyclerViewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void loadData(List<T> t) {
        mListHolder = t;
    }

    @Override
    public int getItemCount() {
        return null == mListHolder ? 0 : mListHolder.size();
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindView(holder, position);
    }

    @Override
    public BossOnlineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.boss_online_view_holder_layout, null);
        BossOnlineViewHolder viewHolder = new BossOnlineViewHolder(view);
        return viewHolder;
    }

    private final class BossOnlineViewHolder extends RecyclerViewHolder {
        private ImageView imageView;
        private TextView about;
        private TextView bosName;
        private TextView bosDuty;
        private TextView workCount;
        private TextView companyName;
        private TextView companyLoc;
        private ImageView bosValidate; // 是否 验证
        private LinearLayout mWorksLayout;
        private TextView mWorksMore;
        private View mView;
        public BossOnlineViewHolder(View view) {
            super(view);

            mView = view;

            imageView = (ImageView) view.findViewById(R.id.boss_online_holder_boss_img);
            about = (TextView) view.findViewById(R.id.boss_online_holder_about);
            bosName = (TextView) view.findViewById(R.id.boss_online_holder_boss_name);
            bosDuty = (TextView) view.findViewById(R.id.boss_online_holder_boss_duty);
            workCount = (TextView) view.findViewById(R.id.boss_online_holder_works_number);
            companyName = (TextView) view.findViewById(R.id.boss_online_holder_company_name);
//            companyLoc = (TextView) view.findViewById(R.id.boss_online_holder_boss_img);
            mWorksLayout = (LinearLayout) view.findViewById(R.id.boss_online_holder_works_layout);
            mWorksMore = (TextView) view.findViewById(R.id.boss_online_holder_works_more);
        }
    }

    @Override
    protected <E extends RecyclerViewHolder> void bindView(E baseHolder, int position) {
        BossOnlineViewHolder holder = (BossOnlineViewHolder) baseHolder;
        if (position < mListHolder.size() && mListHolder.get(position) instanceof BossOnlineDataModel) {
            int validate = ResUtils.getColor(mContext, R.color.boss_online_name_validate_color);
            int normal = ResUtils.getColor(mContext, R.color.boss_online_name_normal_color);
            BossOnlineDataModel model = (BossOnlineDataModel) mListHolder.get(position);
            loadImage(model.getBossImg(), holder.imageView);
            holder.bosName.setTextColor(1 == model.getBossValidate() ? validate : normal);
            holder.bosName.setText(model.getBossName());
            holder.bosDuty.setText(model.getBossDuty());
            holder.workCount.setText(String.format(ResUtils.getString(mContext, R.string.boss_online_work_total), model.getWorkTotal()));
            holder.companyName.setText(model.getCompanyName());
            if (model.getWorkTotal() > 3) {
                holder.mWorksMore.setVisibility(View.VISIBLE);
            } else {
                holder.mWorksMore.setVisibility(View.GONE);
            }
            List<BossOnlineWorkModel> works = model.getWorkLists();
            if (holder.mWorksLayout.getChildCount() > 0) {
                holder.mWorksLayout.removeAllViews();
            }
            for (int i = 0; i < works.size(); ++i) {
                BossOnlineWorkModel work = works.get(i);
                View view = LayoutInflater.from(mContext).inflate(R.layout.boss_online_work_item_layout, null);

                TextView property = (TextView) view.findViewById(R.id.boss_online_work_property);
                TextView duty = (TextView) view.findViewById(R.id.boss_online_work_duty);
                TextView num = (TextView) view.findViewById(R.id.boss_online_workers);
                TextView time = (TextView) view.findViewById(R.id.boss_online_work_publish_time);
                TextView salary = (TextView) view.findViewById(R.id.boss_online_work_salary);
                View line = view.findViewById(R.id.boss_online_item_view_line);
                if (i == works.size() - 1) {
                    line.setVisibility(View.INVISIBLE);
                }

                if (work.getWorkProperty() == 1) {
                    // 是否是兼职
                    property.setBackgroundResource(R.drawable.boss_online_item_work_property_full_time);
                    property.setText(ResUtils.getString(mContext, R.string.boss_online_work_full_time));
                } else {
                    property.setBackgroundResource(R.drawable.boss_online_item_work_property_part_time);
                    property.setText(ResUtils.getString(mContext, R.string.boss_online_work_part_time));
                }

                duty.setText(work.getWorkDuty());
                num.setText(StringUtils.richString(String.format(ResUtils.getString(mContext, R.string.boss_online_employ_workers), work.getWorkers())));
                time.setText(StringUtils.formatTime(work.getWorkPublishTime(), "MM.dd/yy"));
                salary.setText(work.getWorkSalary());
                holder.mWorksLayout.addView(view, i);
            }
        }
    }
}
