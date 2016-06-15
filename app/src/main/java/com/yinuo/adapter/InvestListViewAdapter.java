package com.yinuo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.base.SuperAdapter;
import com.yinuo.adapter.base.SuperViewHolder;
import com.yinuo.base.BaseObject;
import com.yinuo.listener.IOnItemClickListener;
import com.yinuo.mode.InvestWeChatModel;
import com.yinuo.utils.BitmapUtils;
import com.yinuo.utils.ResUtils;
import com.yinuo.utils.StringUtils;

import java.util.List;

/**
 * Created by ludexiang on 2016/5/4.
 */
public class InvestListViewAdapter extends SuperAdapter {

    private Context mContext;
    private List<InvestWeChatModel> mItems;
    private SparseArray<String> mShowTimePosition = new SparseArray<String>();
    private long mLastShowTime;

    public InvestListViewAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void setList(List<InvestWeChatModel> models) {
        mItems = models;
    }

    @Override
    protected List<InvestWeChatModel> getList() {
        return mItems;
    }

    @Override
    public int getCount() {
        return null != mItems ? mItems.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null != mItems ? mItems.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == mItems.get(position).getType()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    protected View getView(int position) {
        int type = getItemViewType(position);
        if (0 == type) {
            return mInflater.inflate(R.layout.invest_wechat_left_layout, null);
        } else {
            return mInflater.inflate(R.layout.invest_wechat_right_layout, null);
        }
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected void initHolder(SuperViewHolder superHolder, int position, View view) {
        if (superHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) superHolder;
            holder.headerImg = (ImageView) view.findViewById(R.id.invest_wechat_header);
            holder.msgTime = (TextView) view.findViewById(R.id.invest_wechat_send_msg_time);
            holder.msgBody = (TextView) view.findViewById(R.id.invest_wechat_msg_body);
        }
    }

    @Override
    protected void bindView(SuperViewHolder superHolder, BaseObject base) {
        String showTime = "";
        if (superHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) superHolder;
            if (base instanceof InvestWeChatModel) {
                InvestWeChatModel model = (InvestWeChatModel) base;
                int position = mItems.indexOf(model);
                if (position == 0) {
                    mShowTimePosition.put(position, model.getMsgTime());
                    mLastShowTime = StringUtils.stringToLong(model.getMsgTime(), null);
                } else {
                    long time = StringUtils.stringToLong(model.getMsgTime(), null);
                    showTime = StringUtils.investWechatTime(mLastShowTime, time);
                }
                if (!StringUtils.isEmpty(mShowTimePosition.get(position))) {
                    holder.msgTime.setVisibility(View.VISIBLE);
                    holder.msgTime.setText(mShowTimePosition.get(position));
                } else {
                    holder.msgTime.setVisibility(View.GONE);
                }
                holder.msgBody.setText(model.getMessage());
                loadImage(model.getHeaderImg(), holder.headerImg);

                Log.e("ldx", "model ==> " + model);
            }
        }
    }

    @Override
    protected void loadBitmapSuccess(Bitmap bitmap, ImageView imageView) {
        imageView.setImageBitmap(BitmapUtils.circularBitmap(bitmap, ResUtils.getDimen(mContext, R.dimen.invest_wechat_header_wh)));
    }

    @Override
    public void setItemClickListener(IOnItemClickListener listener) {

    }

    private final class ViewHolder extends SuperViewHolder {
        private TextView msgTime;
        private ImageView headerImg;
        private TextView msgBody;
        private int position;

        @Override
        protected void setPosition(int p) {
            position = p;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
