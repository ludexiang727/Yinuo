package com.yinuo.ui.component.widget.view.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yinuo.R;

/**
 * Created by ludexiang on 16/5/31.
 */
public class FastClearEditLayout extends RelativeLayout {

    private LayoutInflater mInflater;
    private int mLeftDrawableRes;
    private boolean isPwd;

    private ImageView mLeftImg;
    private ImageView mFastClear;
    private EditText mEdit;

    public FastClearEditLayout(Context context) {
        this(context, null);
    }

    public FastClearEditLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastClearEditLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FastClearEditLayout);
        mLeftDrawableRes = a.getResourceId(R.styleable.FastClearEditLayout_left_img, 0);
        isPwd = a.getBoolean(R.styleable.FastClearEditLayout_is_pwd, false);
        a.recycle();

        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        if (getChildCount() > 0) {
            removeAllViews();
        }

        View view = mInflater.inflate(R.layout.fast_clear_edit_layout, this, true);
        mLeftImg = (ImageView) view.findViewById(R.id.fast_clear_edit_left_img);
        mFastClear = (ImageView) view.findViewById(R.id.fast_clear_edit_clear);
        mEdit = (EditText) view.findViewById(R.id.fast_clear_edit_txt);

    }
}
