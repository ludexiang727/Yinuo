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
import com.yinuo.mode.BossOnlineDataModel;
import com.yinuo.mode.BossOnlineWorkModel;

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
        private TextView bosName;
        private TextView bosDuty;
        private TextView workCount;
        private TextView companyName;
        private TextView companyLoc;
        private ImageView bosValidate; // 是否 验证
        private LinearLayout mWorksLayout;
        private View mView;
        public BossOnlineViewHolder(View view) {
            super(view);

            mView = view;

            imageView = (ImageView) view.findViewById(R.id.boss_online_holder_boss_img);
            bosName = (TextView) view.findViewById(R.id.boss_online_holder_boss_name);
            bosDuty = (TextView) view.findViewById(R.id.boss_online_holder_boss_duty);
            workCount = (TextView) view.findViewById(R.id.boss_online_holder_works_number);
            companyName = (TextView) view.findViewById(R.id.boss_online_holder_company_name);
//            companyLoc = (TextView) view.findViewById(R.id.boss_online_holder_boss_img);
            mWorksLayout = (LinearLayout) view.findViewById(R.id.boss_online_holder_works_layout);
        }
    }

    @Override
    protected <E extends RecyclerViewHolder> void bindView(E baseHolder, int position) {
        BossOnlineViewHolder holder = (BossOnlineViewHolder) baseHolder;
        if (position < mListHolder.size() && mListHolder.get(position) instanceof BossOnlineDataModel) {
            BossOnlineDataModel model = (BossOnlineDataModel) mListHolder.get(position);
            loadImage(model.getBossImg(), holder.imageView);
            holder.bosName.setText(model.getBossName());
            holder.bosDuty.setText(model.getBossDuty());
            holder.workCount.setText("共 " + model.getWorkTotal() + "条");
            holder.companyName.setText(model.getCompanyName());
            List<BossOnlineWorkModel> works = model.getWorkLists();
            for (BossOnlineWorkModel work : works) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.boss_online_work_item_layout, null);
                work.getWorkProperty(); // 是否是兼职
                TextView duty = (TextView) view.findViewById(R.id.boss_online_work_duty);
                TextView num = (TextView) view.findViewById(R.id.boss_online_workers);
                TextView time = (TextView) view.findViewById(R.id.boss_online_work_publish_time);
                TextView salary = (TextView) view.findViewById(R.id.boss_online_work_salary);
                duty.setText(work.getWorkDuty());
                num.setText("招 " + work.getWorkers() + " 人");
                time.setText(work.getWorkPublishTime());
                salary.setText(work.getWorkSalary());
                holder.mWorksLayout.addView(view);
            }
        }
    }
}
