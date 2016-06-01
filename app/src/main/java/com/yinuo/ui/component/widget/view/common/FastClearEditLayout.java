package com.yinuo.ui.component.widget.view.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yinuo.R;
import com.yinuo.utils.ResUtils;

/**
 * Created by ludexiang on 16/5/31.
 */
public class FastClearEditLayout extends RelativeLayout implements View.OnClickListener {

    private LayoutInflater mInflater;
    private int mLeftDrawableRes;
    private boolean isPwd;
    private boolean isShowX;
    private boolean isCheckNum;
    private int mEditHint;
    private int mPaddingTB;
    private int mPaddingLR;

    private ImageView mLeftImg;
    private ImageView mFastClear;
    private EditText mEdit;

    private EditWatcher mWatcher;

    public FastClearEditLayout(Context context) {
        this(context, null);
    }

    public FastClearEditLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastClearEditLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaddingTB = ResUtils.getDimen(context, R.dimen.app_fast_edit_padding_tb);
        mPaddingLR = ResUtils.getDimen(context, R.dimen.app_fast_clear_edit_margin_left);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FastClearEditLayout);
        mLeftDrawableRes = a.getResourceId(R.styleable.FastClearEditLayout_left_img, 0);
        isPwd = a.getBoolean(R.styleable.FastClearEditLayout_is_pwd, false);
        isShowX = a.getBoolean(R.styleable.FastClearEditLayout_is_show_x, false);
        isCheckNum = a.getBoolean(R.styleable.FastClearEditLayout_is_check_num, false);
        mEditHint = a.getResourceId(R.styleable.FastClearEditLayout_edit_hint, 0);
        a.recycle();

        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        if (getChildCount() > 0) {
            removeAllViews();
        }

        mWatcher = new EditWatcher();

        View view = mInflater.inflate(R.layout.fast_clear_edit_layout, this, true);
        mLeftImg = (ImageView) view.findViewById(R.id.fast_clear_edit_left_img);
        mFastClear = (ImageView) view.findViewById(R.id.fast_clear_edit_clear);
        mEdit = (EditText) view.findViewById(R.id.fast_clear_edit_txt);

        int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        mFastClear.measure(width, height);
        if (mLeftDrawableRes <= 0) {
            mLeftImg.setVisibility(View.GONE);
            mEdit.setPadding(mPaddingLR, mPaddingTB, mFastClear.getMeasuredWidth() + mPaddingLR, mPaddingTB);
        } else {
            mLeftImg.setImageResource(mLeftDrawableRes);
            mLeftImg.setVisibility(View.VISIBLE);
            mEdit.setPadding(mFastClear.getMeasuredWidth() + mPaddingLR, mPaddingTB, mFastClear.getMeasuredWidth() + mPaddingLR, mPaddingTB);
        }

        if (!isCheckNum) {
            if (isPwd) {
                mEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                mEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            }
        } else {
            mEdit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }

        if (mEditHint > 0) {
            mEdit.setHint(mEditHint);
        }

        mEdit.addTextChangedListener(mWatcher);
        mFastClear.setOnClickListener(this);
    }

    private final class EditWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (isShowX) {
                String content = mEdit.getText().toString();
                int editLen = content.length();
                mFastClear.setVisibility(editLen > 0 ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public EditText getEditText() {
        return mEdit;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fast_clear_edit_clear: {
                mEdit.setText("");
                break;
            }
        }
    }
}
