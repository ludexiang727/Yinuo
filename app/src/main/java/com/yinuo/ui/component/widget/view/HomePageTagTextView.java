package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.utils.ResUtils;

/**
 * Created by ludexiang on 2016/4/7.
 */
public class HomePageTagTextView extends TextView {
    private Resources mRes;
    public enum TagBackGroundType {
        GREEN, RED, BLUE, YELLOW, NONE
    }

    private int mTextSize;
    private int mPadding;
    private TagBackGroundType mTagBackGroundType = TagBackGroundType.NONE;

    public HomePageTagTextView(Context context) {
        this(context, null);
    }

    public HomePageTagTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageTagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRes = getResources();
        mTextSize = ResUtils.getInt(context, R.dimen.home_page_card_detail_tag_size);
        mPadding = ResUtils.getInt(context, R.dimen.home_page_card_detail_tag_padding);
    }

    private void setBackground(TagBackGroundType type) {
        switch(type) {
            case RED: {
                setTextColor(mRes.getColor(android.R.color.holo_red_light));
                setBackgroundResource(R.drawable.home_page_tag_red_bg);
                break;
            }
            case GREEN: {
                setTextColor(mRes.getColor(android.R.color.holo_green_light));
                setBackgroundResource(R.drawable.home_page_tag_green_bg);
                break;
            }
            case BLUE: {
                setTextColor(mRes.getColor(android.R.color.holo_blue_bright));
                setBackgroundResource(R.drawable.home_page_tag_blue_bg);
                break;
            }
            case YELLOW: {
                setTextColor(mRes.getColor(android.R.color.holo_orange_light));
                setBackgroundResource(R.drawable.home_page_tag_yellow_bg);
                break;
            }
            default: {
                setTextColor(mRes.getColor(R.color.home_page_card_tag_none_bg));
                setBackgroundResource(R.drawable.home_page_tag_none_bg);
                break;
            }
        }
    }

    public void setText(String text, TagBackGroundType type) {
        super.setText(text);
        setBackground(type);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        setPadding(mPadding, 0, mPadding, 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = mPadding;
        setLayoutParams(params);
    }
}
