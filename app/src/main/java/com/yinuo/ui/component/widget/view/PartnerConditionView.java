package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yinuo.R;
import com.yinuo.utils.AppUtils;
import com.yinuo.helper.ImageLoaderHelper;

/**
 * Created by ludexiang on 2016/4/19.
 */
public class PartnerConditionView extends TextView implements View.OnClickListener {
    private Bitmap mLeftBitmap;
    private float mDensity;
    private IConditionListener iConditionListener;
    private int mPosition;

    public PartnerConditionView(Context context) {
        this(context, null);
    }

    public PartnerConditionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartnerConditionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PartnerCondition);
        boolean isSelected = a.getBoolean(R.styleable.PartnerCondition_selected, false);
        int color = a.getColor(R.styleable.PartnerCondition_selected_color, 0);
        a.recycle();

        mDensity = AppUtils.obtainDensity(context);
        setOnClickListener(this);
        setTextColor(color);
    }

    /** base on density obtain right bitmap -- url 在app启动时在config接口中下发 */
    public void setLeftDrawable(String uri) {
        StringBuilder builder = new StringBuilder();
        builder.append(uri);
        if (mDensity > 2f) {
            builder.append("?").append("3x.png");
        } else {
            builder.append("?").append("2x.png");
        }
        new Async().execute(builder.toString());
    }

    private class Async extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            ImageLoaderHelper.getInstance().loadImage(url, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (loadedImage != null) {
                        mLeftBitmap = loadedImage;
                    }
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            postExecute();
        }
    }

    private void postExecute() {
        BitmapDrawable drawable = new BitmapDrawable(mLeftBitmap);
        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    @Override
    public void onClick(View v) {
        if (iConditionListener != null) {
            iConditionListener.onConditionClick(mPosition);
        }
    }

    public void setConditionListener(IConditionListener listener) {
        iConditionListener = listener;
    }

    public interface IConditionListener {
        void onConditionClick(int position);
    }

    public void setPosition(int position) {
        mPosition = position;
    }
}
