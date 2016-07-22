package com.yinuo.ui.component.widget.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
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
import com.yinuo.utils.StringUtils;

/**
 * Created by ludexiang on 16/5/31.
 */
public class FastClearEditLayout extends RelativeLayout implements View.OnClickListener, View.OnFocusChangeListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private int mLeftDrawableRes;
    private boolean isPwd;
    private boolean isShowX;
    private boolean isCheckNum;
    private boolean isTel;
    private int mEditHint;
    private int mPaddingTB;
    private int mPaddingLR;

    private ImageView mLeftImg;
    private ImageView mFastClear;
    private EditText mEdit;

    private ITextWatcherListener iTextWatcherListener;

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
        isCheckNum = a.getBoolean(R.styleable.FastClearEditLayout_is_num, false);
        mEditHint = a.getResourceId(R.styleable.FastClearEditLayout_edit_hint, 0);
        isTel = a.getBoolean(R.styleable.FastClearEditLayout_is_tel, false);
        a.recycle();

        init(context);
    }

    private void init(Context context) {
        mContext = context;
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
            Drawable drawable = ResUtils.getDrawable(mContext, mLeftDrawableRes);
            mLeftImg.setImageDrawable(drawable);
            mLeftImg.setVisibility(View.VISIBLE);
            mEdit.setPadding(drawable.getIntrinsicWidth() + mPaddingLR, mPaddingTB, mFastClear.getMeasuredWidth() + mPaddingLR, mPaddingTB);
        }

        if (!isCheckNum) {
            if (isPwd) {
                mEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                mEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            }
        } else {
            if (!isTel) {
                mEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            } else {
                // is tel phone number
                mEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            }
            mEdit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }

        if (mEditHint > 0) {
            mEdit.setHint(mEditHint);
        }
        mEdit.setOnFocusChangeListener(this);
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
            if (iTextWatcherListener != null) {
                iTextWatcherListener.onTextWatcher(s);
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

    public void setTextWatcherListener(ITextWatcherListener listener) {
        iTextWatcherListener = listener;
    }

    public interface ITextWatcherListener {
        void onTextWatcher(CharSequence s);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            mFastClear.setVisibility(View.GONE);
        } else {
            if (!StringUtils.isEmpty(mEdit.getText().toString())) {
                mFastClear.setVisibility(View.VISIBLE);
            }
        }
    }
}
