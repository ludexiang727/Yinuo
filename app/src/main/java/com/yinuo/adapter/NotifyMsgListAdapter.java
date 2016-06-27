package com.yinuo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.SuperAdapter;
import com.yinuo.adapter.base.SuperViewHolder;
import com.yinuo.base.BaseObject;
import com.yinuo.listener.IOnItemClickListener;
import com.yinuo.mode.NotifyMsgModel;

import java.util.List;

/**
 * Created by ludexiang on 16/6/27.
 */
public class NotifyMsgListAdapter extends SuperAdapter {
    private Context mContext;
    private List<NotifyMsgModel> mItems;

    public NotifyMsgListAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void setList(List<NotifyMsgModel> data) {
        mItems = data;
    }

    @Override
    public int getCount() {
        return null != mItems ? mItems.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null != null ? mItems.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected View getView(int position) {
        return mInflater.inflate(R.layout.app_notify_msg_sub_layout, null);
    }

    @Override
    protected SuperViewHolder getViewHolder() {
        return new NotifyViewHolder();
    }

    @Override
    protected void initHolder(SuperViewHolder superHolder, int position, View view) {
        if (superHolder instanceof NotifyViewHolder) {
            NotifyViewHolder holder = (NotifyViewHolder) superHolder;

            holder.mNotifyTime = (TextView) view.findViewById(R.id.app_notify_msg_time);
            holder.mNotifyMsgImg = (ImageView) view.findViewById(R.id.app_notify_img);
            holder.mNotifyMsgTitle = (TextView) view.findViewById(R.id.app_notify_title);
            holder.mNotifyOtherLayout = (LinearLayout) view.findViewById(R.id.app_notify_other_msg_layout);
        }
    }

    @Override
    protected List<NotifyMsgModel> getList() {
        return mItems;
    }

    @Override
    public void setItemClickListener(IOnItemClickListener listener) {

    }



    @Override
    protected void bindView(SuperViewHolder superHolder, BaseObject base) {
        if (superHolder instanceof NotifyViewHolder) {
            NotifyViewHolder holder = (NotifyViewHolder) superHolder;
            if (base instanceof NotifyMsgModel) {
                NotifyMsgModel model = (NotifyMsgModel) base;
                List<NotifyMsgModel.NotifyMsg> notifyMsgs = model.getList();
                if (notifyMsgs == null || notifyMsgs.size() == 0) {
                    return;
                }
                if (holder != null && holder.mNotifyOtherLayout != null) {
                    holder.mNotifyOtherLayout.removeAllViews();
                }
                for (int i = 0; i < notifyMsgs.size(); ++i) {
                    NotifyMsgModel.NotifyMsg msg = notifyMsgs.get(i);
                    if (i == 0) {
                        holder.mNotifyTime.setText(model.getNotifyTime());
                        holder.mNotifyMsgTitle.setText(msg.getNotifyTitle());
                        loadImage(msg.getNotifyImg(), holder.mNotifyMsgImg);
                        continue;
                    }

                    View subView = mInflater.inflate(R.layout.app_notify_sub_layout, null);
                    RelativeLayout parent = (RelativeLayout) subView.findViewById(R.id.app_notify_sub_parent);
                    ImageView imgView = (ImageView) subView.findViewById(R.id.app_notify_sub_img);
                    TextView title = (TextView) subView.findViewById(R.id.app_notify_sub_title);
                    loadImage(msg.getNotifyImg(), imgView);
                    title.setText(msg.getNotifyTitle());
                    holder.mNotifyOtherLayout.addView(subView);
                    parent.setOnClickListener(holder);
                }
            }
        }
    }

    private class NotifyViewHolder extends SuperViewHolder {
        private TextView mNotifyTime;
        private ImageView mNotifyMsgImg;
        private TextView mNotifyMsgTitle;
        private LinearLayout mNotifyOtherLayout;

        @Override
        protected void setPosition(int position) {

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.app_notify_sub_parent: {
                    break;
                }
            }
        }
    }
}
